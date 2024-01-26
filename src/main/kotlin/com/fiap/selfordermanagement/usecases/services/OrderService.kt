package services

import com.fiap.selfordermanagement.adapter.gateway.OrderGateway
import com.fiap.selfordermanagement.adapter.gateway.TransactionalGateway
import com.fiap.selfordermanagement.domain.entities.Order
import com.fiap.selfordermanagement.domain.entities.OrderItem
import com.fiap.selfordermanagement.domain.errors.ErrorType
import com.fiap.selfordermanagement.domain.errors.SelfOrderManagementException
import com.fiap.selfordermanagement.domain.valueobjects.OrderStatus
import com.fiap.selfordermanagement.usecases.AdjustStockUseCase
import com.fiap.selfordermanagement.usecases.CancelOrderStatusUseCase
import com.fiap.selfordermanagement.usecases.CompleteOrderUseCase
import com.fiap.selfordermanagement.usecases.ConfirmOrderUseCase
import com.fiap.selfordermanagement.usecases.LoadCustomerUseCase
import com.fiap.selfordermanagement.usecases.LoadOrderUseCase
import com.fiap.selfordermanagement.usecases.LoadProductUseCase
import com.fiap.selfordermanagement.usecases.PlaceOrderUseCase
import com.fiap.selfordermanagement.usecases.PrepareOrderUseCase
import com.fiap.selfordermanagement.usecases.ProvidePaymentRequestUseCase
import java.time.LocalDate

open class OrderService(
    private val orderRepository: OrderGateway,
    private val getCustomersUseCase: LoadCustomerUseCase,
    private val getProductUseCase: LoadProductUseCase,
    private val adjustInventoryUseCase: AdjustStockUseCase,
    private val providePaymentRequestUseCase: ProvidePaymentRequestUseCase,
    private val loadProductUseCase: LoadProductUseCase,
    private val transactionalRepository: TransactionalGateway,
) : LoadOrderUseCase,
    PlaceOrderUseCase,
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

            val order = orderRepository.upsert(
                Order(
                    number = null,
                    date = LocalDate.now(),
                    customerNickname = customerNickname,
                    customer = customerDocument?.let { getCustomersUseCase.getByDocument(it) },
                    status = OrderStatus.CREATED,
                    items = products,
                    total = products.sumOf { it.price },
                )
            )

            providePaymentRequestUseCase.providePaymentRequest(order)

            order
        }
    }

    override fun confirmOrder(orderNumber: Long): Order {
        TODO("not implemented")
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
