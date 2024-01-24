package services

import com.fiap.selfordermanagement.adapter.gateway.OrderGateway
import com.fiap.selfordermanagement.adapter.gateway.TransactionalGateway
import com.fiap.selfordermanagement.domain.entities.Order
import com.fiap.selfordermanagement.domain.entities.OrderItem
import com.fiap.selfordermanagement.domain.entities.PaymentRequest
import com.fiap.selfordermanagement.domain.errors.ErrorType
import com.fiap.selfordermanagement.domain.errors.SelfOrderManagementException
import com.fiap.selfordermanagement.domain.valueobjects.OrderStatus
import com.fiap.selfordermanagement.domain.valueobjects.PaymentStatus
import com.fiap.selfordermanagement.usecases.*
import java.time.LocalDate

open class OrderService(
    private val orderRepository: OrderGateway,
    private val getCustomersUseCase: LoadCustomerUseCase,
    private val getProductUseCase: LoadProductUseCase,
    private val adjustInventoryUseCase: AdjustStockUseCase,
    private val loadPaymentUseCase: LoadPaymentUseCase,
    private val providePaymentRequestUseCase: ProvidePaymentRequestUseCase,
    private val syncPaymentStatusUseCase: SyncPaymentStatusUseCase,
    private val transactionalRepository: TransactionalGateway,
) : LoadOrderUseCase,
    PlaceOrderUseCase,
    IntentOrderPaymentUseCase,
    ConfirmOrderUseCase,
    PrepareOrderUseCase,
    CompleteOrderUseCase,
    CancelOrderStatusUseCase {
    override fun getByOrderNumber(orderNumber: Long): Order {
        return orderRepository.findByOrderNumber(orderNumber)
            ?: throw SelfOrderManagementException(
                errorType = ErrorType.ORDER_NOT_FOUND,
                message = "Order [$orderNumber] not found",
            )
    }

    override fun findAll(): List<Order> {
        return orderRepository.findAllActiveOrders()
    }

    override fun findByStatus(status: OrderStatus): List<Order> {
        return orderRepository.findByStatus(status)
    }

    override fun findByCustomerNickname(nickname: String): List<Order> {
        return orderRepository.findByCustomerNickname(nickname)
    }

    override fun findByCustomerNicknameAndStatus(
        nickname: String,
        status: OrderStatus,
    ): List<Order> {
        return orderRepository.findByCustomerNicknameAndStatus(nickname, status)
    }

    override fun findByCustomerDocument(document: String): List<Order> {
        return orderRepository.findByCustomerDocument(document)
    }

    override fun findByCustomerDocumentAndStatus(
        document: String,
        status: OrderStatus,
    ): List<Order> {
        return orderRepository.findByCustomerDocumentAndStatus(document, status)
    }

    override fun create(
        customerNickname: String,
        customerDocument: String?,
        items: List<OrderItem>,
    ): Order {
        return transactionalRepository.transaction {
            if (items.isEmpty()) {
                throw SelfOrderManagementException(
                    errorType = ErrorType.EMPTY_ORDER,
                    message = "Empty order",
                )
            }

            val products =
                items.flatMap {
                    val product = getProductUseCase.getByProductNumber(it.productNumber)
                    if (!product.isLogicalItem()) {
                        product.components.mapNotNull { p -> p.number }.forEach { componentNumber ->
                            adjustInventoryUseCase.decrement(componentNumber, it.quantity)
                        }
                    }
                    MutableList(it.quantity.toInt()) { product }
                }

            val order =
                Order(
                    number = null,
                    date = LocalDate.now(),
                    customerNickname = customerNickname,
                    customer = customerDocument?.let { getCustomersUseCase.getByDocument(it) },
                    status = OrderStatus.CREATED,
                    items = products,
                    total = products.sumOf { it.price },
                )

            orderRepository.upsert(order)
        }
    }

    override fun intentToPayOrder(orderNumber: Long): PaymentRequest {
        return transactionalRepository.transaction {
            val order = getByOrderNumber(orderNumber)

            val paymentRequest =
                when (order.status) {
                    OrderStatus.CREATED -> {
                        orderRepository.upsert(order.copy(status = OrderStatus.PENDING))
                        providePaymentRequestUseCase.provideNew(orderNumber, order.total)
                    }

                    OrderStatus.PENDING -> {
                        loadPaymentUseCase.getByOrderNumber(orderNumber)
                            .let { providePaymentRequestUseCase.provideWith(order, it) }
                    }

                    OrderStatus.REJECTED -> {
                        orderRepository.upsert(order.copy(status = OrderStatus.PENDING))
                        loadPaymentUseCase.findByOrderNumber(orderNumber)?.let { payment ->
                            providePaymentRequestUseCase.provideWith(order, payment)
                        } ?: providePaymentRequestUseCase.provideNew(orderNumber, order.total)
                    }

                    else -> throw SelfOrderManagementException(
                        errorType = ErrorType.PAYMENT_REQUEST_NOT_ALLOWED,
                        message =
                            "Payment requests can be made only retrieved for orders in the created state," +
                                " or in the pending state, or in the rejected state when retrying",
                    )
                }

            paymentRequest
        }
    }

    override fun confirmOrder(orderNumber: Long): Order {
        return transactionalRepository.transaction {
            val order = getByOrderNumber(orderNumber)

            val payment = order.number?.let(loadPaymentUseCase::findByOrderNumber)
            payment ?: run {
                orderRepository.upsert(order.copy(status = OrderStatus.REJECTED))
                throw SelfOrderManagementException(
                    errorType = ErrorType.PAYMENT_NOT_FOUND,
                    message = "Payment not found for order [$orderNumber]",
                )
            }

            syncPaymentStatusUseCase.syncPaymentStatus(payment)
                .takeIf { it.status == PaymentStatus.CONFIRMED }
                ?: run {
                    orderRepository.upsert(order.copy(status = OrderStatus.REJECTED))
                    throw SelfOrderManagementException(
                        errorType = ErrorType.PAYMENT_NOT_CONFIRMED,
                        message = "Last payment not confirmed for order $orderNumber",
                    )
                }

            when (order.status) {
                OrderStatus.REJECTED, OrderStatus.PENDING -> {
                    orderRepository.upsert(order.copy(status = OrderStatus.CONFIRMED))
                }

                else -> {
                    throw SelfOrderManagementException(
                        errorType = ErrorType.INVALID_ORDER_STATE_TRANSITION,
                        message =
                            "Confirmation is only allowed for orders that are in a pending state " +
                                "and have not been confirmed previously, or when retrying after payment confirmation",
                    )
                }
            }
        }
    }

    override fun startOrderPreparation(orderNumber: Long): Order {
        return getByOrderNumber(orderNumber)
            .takeIf { it.status == OrderStatus.CONFIRMED }
            ?.run {
                orderRepository.upsert(copy(status = OrderStatus.PREPARING))
            }
            ?: throw SelfOrderManagementException(
                errorType = ErrorType.INVALID_ORDER_STATE_TRANSITION,
                message = "Preparation of the order cannot begin until it has been confirmed",
            )
    }

    override fun completeOrder(orderNumber: Long): Order {
        return getByOrderNumber(orderNumber)
            .takeIf { it.status == OrderStatus.PREPARING }
            ?.run {
                orderRepository.upsert(copy(status = OrderStatus.COMPLETED))
            }
            ?: throw SelfOrderManagementException(
                errorType = ErrorType.INVALID_ORDER_STATE_TRANSITION,
                message = "Order cannot be completed until it has been prepared",
            )
    }

    override fun finishOrderPreparation(orderNumber: Long): Order {
        return getByOrderNumber(orderNumber)
            .takeIf { it.status == OrderStatus.COMPLETED }
            ?.run {
                orderRepository.upsert(copy(status = OrderStatus.DONE))
            }
            ?: throw SelfOrderManagementException(
                errorType = ErrorType.INVALID_ORDER_STATE_TRANSITION,
                message = "Order cannot be finished until it has been completed (delivered)",
            )
    }

    override fun cancelOrder(orderNumber: Long): Order {
        return transactionalRepository.transaction {
            getByOrderNumber(orderNumber)
                .takeIf { it.status != OrderStatus.COMPLETED && it.status != OrderStatus.DONE }
                ?.run {
                    if (status == OrderStatus.CREATED || status == OrderStatus.CONFIRMED) {
                        // in this case, make reserved products available again
                        items.forEach {
                            it.number?.let { number -> adjustInventoryUseCase.increment(number, 1) }
                        }
                    }
                    orderRepository.upsert(copy(status = OrderStatus.CANCELLED))
                }
                ?: throw SelfOrderManagementException(
                    errorType = ErrorType.INVALID_ORDER_STATE_TRANSITION,
                    message = "This order has already been marked as completed",
                )
        }
    }
}
