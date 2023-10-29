package com.fiap.selfordermanagement.application.services

import com.fiap.selfordermanagement.adapters.driver.web.request.OrderItemRequest
import com.fiap.selfordermanagement.application.domain.entities.Order
import com.fiap.selfordermanagement.application.domain.entities.OrderItem
import com.fiap.selfordermanagement.application.domain.entities.PaymentRequest
import com.fiap.selfordermanagement.application.domain.errors.ErrorType
import com.fiap.selfordermanagement.application.domain.errors.SelfOrderManagementException
import com.fiap.selfordermanagement.application.domain.valueobjects.OrderStatus
import com.fiap.selfordermanagement.application.domain.valueobjects.PaymentStatus
import com.fiap.selfordermanagement.application.ports.incoming.*
import com.fiap.selfordermanagement.application.ports.outgoing.OrderRepository
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

open class OrderService(
    private val orderRepository: OrderRepository,
    private val getCustomersUseCase: LoadCustomerUseCase,
    private val getProductUseCase: LoadProductUseCase,
    private val getStockUseCase: LoadStockUseCase,
    private val adjustInventoryUseCase: AdjustInventoryUseCase,
    private val loadPaymentUseCase: LoadPaymentUseCase,
    private val providePaymentRequestUseCase: ProvidePaymentRequestUseCase,
    private val syncPaymentStatusUseCase: SyncPaymentStatusUseCase,
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
                message = "Order $orderNumber not found",
            )
    }

    override fun findAll(): List<Order> {
        return orderRepository.findAll()
    }

    override fun findByStatus(status: OrderStatus): List<Order> {
        return orderRepository.findByStatus(status)
    }

    // TODO: expose in endpoint
    override fun findByCustomerNickname(nickname: String): List<Order> {
        return orderRepository.findByCustomerNickname(nickname)
    }

    // TODO: expose in endpoint
    override fun findByCustomerNicknameAndStatus(
        nickname: String,
        status: OrderStatus,
    ): List<Order> {
        return orderRepository.findByCustomerNicknameAndStatus(nickname, status)
    }

    // TODO: expose in endpoint
    override fun findByCustomerDocument(document: String): List<Order> {
        return orderRepository.findByCustomerDocument(document)
    }

    // TODO: expose in endpoint
    override fun findByCustomerDocumentAndStatus(
        document: String,
        status: OrderStatus,
    ): List<Order> {
        return orderRepository.findByCustomerDocumentAndStatus(document, status)
    }

    @Transactional
    override fun create(
        customerNickname: String,
        customerDocument: String?,
        items: List<OrderItemRequest>,
    ): Order {
        if (items.isEmpty()) {
            throw SelfOrderManagementException(
                errorType = ErrorType.EMPTY_ORDER,
                message = "Empty order",
            )
        }

        val orderItems =
            items.map {
                val product = getProductUseCase.getByProductNumber(it.productNumber)
                val stock = getStockUseCase.getByProductNumber(it.productNumber)
                if (!product.isLogicalItem()) {
                    product.inputs.mapNotNull { p -> p.number }.forEach { inputNumber ->
                        adjustInventoryUseCase.decrement(inputNumber, it.quantity)
                    }
                }
                OrderItem(it.productNumber, it.quantity, product.price)
            }

        val order =
            Order(
                number = null,
                date = LocalDate.now(),
                customerNickname = customerNickname,
                customer = customerDocument?.let { getCustomersUseCase.getByDocument(it) },
                status = OrderStatus.CREATED,
                items = orderItems,
                total = orderItems.sumOf { it.price },
            )

        return orderRepository.upsert(order)
    }

    @Transactional
    override fun intentToPayOrder(orderNumber: Long): PaymentRequest {
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

        return paymentRequest
    }

    @Transactional
    override fun confirmOrder(orderNumber: Long): Order {
        val order = getByOrderNumber(orderNumber)

        val payment = order.number?.let(loadPaymentUseCase::findByOrderNumber)
        payment ?: run {
            orderRepository.upsert(order.copy(status = OrderStatus.REJECTED))
            throw SelfOrderManagementException(
                errorType = ErrorType.PAYMENT_NOT_FOUND,
                message = "Payment not found for order $orderNumber",
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

        return when (order.status) {
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

    override fun finishOrderPreparation(orderNumber: Long): Order {
        return getByOrderNumber(orderNumber)
            .takeIf { it.status == OrderStatus.PREPARING }
            ?.run {
                orderRepository.upsert(copy(status = OrderStatus.DONE))
            }
            ?: throw SelfOrderManagementException(
                errorType = ErrorType.INVALID_ORDER_STATE_TRANSITION,
                message = "Order cannot be moved out of preparation because it is not currently in preparation",
            )
    }

    override fun completeOrder(orderNumber: Long): Order {
        return getByOrderNumber(orderNumber)
            .takeIf { it.status != OrderStatus.DONE }
            ?.run {
                orderRepository.upsert(copy(status = OrderStatus.COMPLETED))
            }
            ?: throw SelfOrderManagementException(
                errorType = ErrorType.INVALID_ORDER_STATE_TRANSITION,
                message = "This order cannot be completed as it has not been finished yet",
            )
    }

    @Transactional
    override fun cancelOrder(orderNumber: Long): Order {
        return getByOrderNumber(orderNumber)
            .takeIf { it.status != OrderStatus.COMPLETED }
            ?.run {
                if (status == OrderStatus.CREATED || status == OrderStatus.CONFIRMED) {
                    // in this case, make reserved products available again
                    items.forEach {
                        adjustInventoryUseCase.increment(it.productNumber, it.quantity)
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
