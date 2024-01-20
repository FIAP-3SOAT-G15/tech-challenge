package com.fiap.selfordermanagement.application.services

import com.fiap.selfordermanagement.application.adapter.repository.OrderRepository
import com.fiap.selfordermanagement.application.adapter.services.OrderService
import com.fiap.selfordermanagement.application.domain.entities.OrderItem
import com.fiap.selfordermanagement.application.domain.errors.ErrorType
import com.fiap.selfordermanagement.application.domain.errors.SelfOrderManagementException
import com.fiap.selfordermanagement.application.domain.valueobjects.OrderStatus
import com.fiap.selfordermanagement.application.domain.valueobjects.PaymentStatus
import com.fiap.selfordermanagement.application.usecases.*
import com.fiap.selfordermanagement.database.persistence.TransactionalRepositoryImpl
import createCustomer
import createOrder
import createOrderItem
import createPayment
import createPaymentRequest
import createProduct
import createStock
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import java.math.BigDecimal

class OrderServiceTest {
    private val orderRepository = mockk<OrderRepository>()
    private val getCustomersUseCase = mockk<LoadCustomerUseCase>()
    private val getProductUseCase = mockk<LoadProductUseCase>()
    private val adjustInventoryUseCase = mockk<AdjustStockUseCase>()
    private val loadPaymentUseCase = mockk<LoadPaymentUseCase>()
    private val providePaymentRequestUseCase = mockk<ProvidePaymentRequestUseCase>()
    private val syncPaymentStatusUseCase = mockk<SyncPaymentStatusUseCase>()
    private val transactionalRepository = TransactionalRepositoryImpl()

    private val orderService =
        OrderService(
            orderRepository,
            getCustomersUseCase,
            getProductUseCase,
            adjustInventoryUseCase,
            loadPaymentUseCase,
            providePaymentRequestUseCase,
            syncPaymentStatusUseCase,
            transactionalRepository,
        )

    @BeforeEach
    fun setUp() {
        every { getCustomersUseCase.getByDocument(any()) } returns createCustomer()
        every { getProductUseCase.getByProductNumber(any()) } returns createProduct()
        every { loadPaymentUseCase.getByOrderNumber(any()) } returns createPayment()
        every { loadPaymentUseCase.findByOrderNumber(any()) } returns createPayment()
        every { providePaymentRequestUseCase.provideNew(any(), any()) } returns createPaymentRequest()
        every { providePaymentRequestUseCase.provideWith(any(), any()) } returns createPaymentRequest()
    }

    @AfterEach
    fun tearDown() {
        unmockkAll()
    }

    @Nested
    inner class GetByOrderNumberTest {
        @Test
        fun `getByOrderNumber should return an Order when it exists`() {
            val order = createOrder()

            every { orderRepository.findByOrderNumber(order.number!!) } returns order

            val result = orderService.getByOrderNumber(order.number!!)

            assertThat(result).isEqualTo(order)
        }

        @Test
        fun `getByOrderNumber should throw an exception when the order is not found`() {
            val orderNumber = 67890L

            every { orderRepository.findByOrderNumber(orderNumber) } returns null

            assertThatThrownBy { orderService.getByOrderNumber(orderNumber) }
                .isInstanceOf(SelfOrderManagementException::class.java)
                .hasFieldOrPropertyWithValue("errorType", ErrorType.ORDER_NOT_FOUND)
        }
    }

    @Nested
    inner class CreateTest {
        @Test
        fun `create should return a valid Order when items are provided`() {
            val customerNickname = "Fulano"
            val items = listOf(createOrderItem())

            every { adjustInventoryUseCase.decrement(any(), any()) } returns createStock()
            every { orderRepository.upsert(any()) } returns createOrder(status = OrderStatus.CREATED)

            val result = orderService.create(customerNickname, null, items)

            assertThat(result).isNotNull()
            assertThat(result.number).isNotNull()
            assertThat(result.customerNickname).isEqualTo(customerNickname)
            assertThat(result.items).hasSize(1)
            assertThat(result.total).isEqualTo(BigDecimal("50.00"))
        }

        @Test
        fun `create should throw an exception when items are empty`() {
            val customerNickname = "Fulano"
            val items = emptyList<OrderItem>()

            assertThatThrownBy { orderService.create(customerNickname, null, items) }
                .isInstanceOf(SelfOrderManagementException::class.java)
                .hasFieldOrPropertyWithValue("errorType", ErrorType.EMPTY_ORDER)
        }
    }

    @Nested
    inner class IntentToPay {
        @ParameterizedTest
        @EnumSource(OrderStatus::class, names = ["CREATED", "PENDING", "REJECTED"])
        fun `intentToPayOrder should return a PaymentRequest in the valid state`(orderStatus: OrderStatus) {
            val order = createOrder(status = orderStatus)

            every { orderRepository.findByOrderNumber(any()) } returns order
            every { orderRepository.upsert(any()) } answers { firstArg() }

            val result = orderService.intentToPayOrder(order.number!!)

            assertThat(result).isNotNull()
            assertThat(result.externalId.toString()).isEqualTo("66b0f5f7-9997-4f49-a203-3dab2d936b50")
            assertThat(result.qrCode).isEqualTo("U8OpcmlvPw==")
        }

        @Test
        fun `intentToPayOrder should return a PaymentRequest in PENDING state with existing payment`() {
            val order = createOrder(status = OrderStatus.PENDING)

            every { orderRepository.findByOrderNumber(any()) } returns order
            every { orderRepository.upsert(order) } answers { firstArg() }

            val result = orderService.intentToPayOrder(order.number!!)

            assertThat(result).isNotNull()
            assertThat(result.externalId.toString()).isEqualTo("66b0f5f7-9997-4f49-a203-3dab2d936b50")
            assertThat(result.qrCode).isEqualTo("U8OpcmlvPw==")
        }

        @ParameterizedTest
        @EnumSource(OrderStatus::class, names = ["CREATED", "PENDING", "REJECTED"], mode = EnumSource.Mode.EXCLUDE)
        fun `intentToPayOrder should throw an exception for an unsupported state`(orderStatus: OrderStatus) {
            val order = createOrder(status = orderStatus)

            every { orderRepository.findByOrderNumber(any()) } returns order
            every { orderRepository.upsert(order) } answers { firstArg() }

            assertThatThrownBy { orderService.intentToPayOrder(order.number!!) }
                .isInstanceOf(SelfOrderManagementException::class.java)
                .hasFieldOrPropertyWithValue("errorType", ErrorType.PAYMENT_REQUEST_NOT_ALLOWED)
        }
    }

    @Nested
    inner class ConfirmOrderTest {
        @ParameterizedTest
        @EnumSource(OrderStatus::class, names = ["PENDING", "REJECTED"])
        fun `confirmOrder should confirm a PENDING or REJECTED order with a valid payment`(orderStatus: OrderStatus) {
            val order = createOrder(status = orderStatus)

            every { orderRepository.findByOrderNumber(any()) } returns order
            every { syncPaymentStatusUseCase.syncPaymentStatus(any()) } returns createPayment(status = PaymentStatus.CONFIRMED)
            every { orderRepository.upsert(any()) } answers { firstArg() }

            val result = orderService.confirmOrder(order.number!!)

            assertThat(result).isNotNull()
            assertThat(result.status).isEqualTo(OrderStatus.CONFIRMED)
        }

        @Test
        fun `confirmOrder should throw an exception when the payment is not found`() {
            val order = createOrder(status = OrderStatus.PENDING)

            every { orderRepository.findByOrderNumber(any()) } returns order
            every { loadPaymentUseCase.findByOrderNumber(any()) } returns null
            every { orderRepository.upsert(any()) } answers { firstArg() }

            assertThatThrownBy { orderService.confirmOrder(order.number!!) }
                .isInstanceOf(SelfOrderManagementException::class.java)
                .hasFieldOrPropertyWithValue("errorType", ErrorType.PAYMENT_NOT_FOUND)
        }

        @Test
        fun `confirmOrder should throw an exception when the payment is not confirmed`() {
            val order = createOrder(status = OrderStatus.PENDING)

            every { orderRepository.findByOrderNumber(any()) } returns order
            every { orderRepository.upsert(order) } returns order
            every { loadPaymentUseCase.findByOrderNumber(any()) } returns createPayment(status = PaymentStatus.PENDING)
            every { syncPaymentStatusUseCase.syncPaymentStatus(any()) } returns createPayment(status = PaymentStatus.PENDING)
            every { orderRepository.upsert(any()) } answers { firstArg() }

            assertThatThrownBy { orderService.confirmOrder(order.number!!) }
                .isInstanceOf(SelfOrderManagementException::class.java)
                .hasFieldOrPropertyWithValue("errorType", ErrorType.PAYMENT_NOT_CONFIRMED)
        }

        @Test
        fun `confirmOrder should throw an exception for an unsupported order state`() {
            val order = createOrder(status = OrderStatus.DONE)

            every { orderRepository.findByOrderNumber(any()) } returns order
            every { orderRepository.upsert(order) } answers { firstArg() }
            every { loadPaymentUseCase.findByOrderNumber(any()) } returns createPayment(status = PaymentStatus.CONFIRMED)
            every { syncPaymentStatusUseCase.syncPaymentStatus(any()) } returns createPayment(status = PaymentStatus.CONFIRMED)

            assertThatThrownBy { orderService.confirmOrder(order.number!!) }
                .isInstanceOf(SelfOrderManagementException::class.java)
                .hasFieldOrPropertyWithValue("errorType", ErrorType.INVALID_ORDER_STATE_TRANSITION)
        }
    }

    @Nested
    inner class StartOrderPreparationTest {
        @Test
        fun `startOrderPreparation should start preparation for a CONFIRMED order`() {
            val order = createOrder(status = OrderStatus.CONFIRMED)

            every { orderRepository.findByOrderNumber(any()) } returns order
            every { orderRepository.upsert(any()) } answers { firstArg() }

            val result = orderService.startOrderPreparation(order.number!!)

            assertThat(result).isNotNull()
            assertThat(result.status).isEqualTo(OrderStatus.PREPARING)
        }

        @Test
        fun `startOrderPreparation should throw an exception for a non-CONFIRMED order`() {
            val order = createOrder(status = OrderStatus.CREATED)

            every { orderRepository.findByOrderNumber(any()) } returns order

            assertThatThrownBy { orderService.startOrderPreparation(order.number!!) }
                .isInstanceOf(SelfOrderManagementException::class.java)
                .hasFieldOrPropertyWithValue("errorType", ErrorType.INVALID_ORDER_STATE_TRANSITION)
        }
    }

    @Nested
    inner class FinishOrderPreparationTest {
        @Test
        fun `finishOrderPreparation should finish a COMPLETED order when it is delivered`() {
            val order = createOrder(status = OrderStatus.COMPLETED)

            every { orderRepository.findByOrderNumber(any()) } returns order
            every { orderRepository.upsert(any()) } answers { firstArg() }

            val result = orderService.finishOrderPreparation(order.number!!)

            assertThat(result).isNotNull()
            assertThat(result.status).isEqualTo(OrderStatus.DONE)
        }

        @Test
        fun `finishOrderPreparation should throw an exception for a non-PREPARING order`() {
            val order = createOrder(status = OrderStatus.CREATED)

            every { orderRepository.findByOrderNumber(any()) } returns order

            assertThatThrownBy { orderService.finishOrderPreparation(order.number!!) }
                .isInstanceOf(SelfOrderManagementException::class.java)
                .hasFieldOrPropertyWithValue("errorType", ErrorType.INVALID_ORDER_STATE_TRANSITION)
        }
    }

    @Nested
    inner class CompleteOrderTest {
        @Test
        fun `completeOrder should complete an order that is not yet completed (status is not DONE)`() {
            val order = createOrder(status = OrderStatus.PREPARING)

            every { orderRepository.findByOrderNumber(any()) } returns order
            every { orderRepository.upsert(any()) } answers { firstArg() }

            val result = orderService.completeOrder(order.number!!)

            assertThat(result).isNotNull()
            assertThat(result.status).isEqualTo(OrderStatus.COMPLETED)
        }

        @Test
        fun `completeOrder should throw an exception for an already completed order (status is DONE)`() {
            val order = createOrder(status = OrderStatus.DONE)

            every { orderRepository.findByOrderNumber(any()) } returns order

            assertThatThrownBy { orderService.completeOrder(order.number!!) }
                .isInstanceOf(SelfOrderManagementException::class.java)
                .hasFieldOrPropertyWithValue("errorType", ErrorType.INVALID_ORDER_STATE_TRANSITION)
                .hasMessage("Order cannot be completed until it has been prepared")
        }
    }

    @Nested
    inner class CancelOrderTest {
        @Test
        fun `cancelOrder should cancel a CREATED order and make reserved products available`() {
            val order = createOrder(status = OrderStatus.CREATED)

            every { orderRepository.findByOrderNumber(any()) } returns order
            every { orderRepository.upsert(any()) } answers { firstArg() }
            every { adjustInventoryUseCase.increment(any(), any()) } returns createStock()

            val result = orderService.cancelOrder(order.number!!)

            assertThat(result).isNotNull()
            assertThat(result.status).isEqualTo(OrderStatus.CANCELLED)
        }

        @Test
        fun `cancelOrder should cancel a CONFIRMED order and make reserved products available`() {
            val order = createOrder(status = OrderStatus.CONFIRMED)

            every { orderRepository.findByOrderNumber(any()) } returns order
            every { orderRepository.upsert(any()) } answers { firstArg() }
            every { adjustInventoryUseCase.increment(any(), any()) } returns createStock()

            val result = orderService.cancelOrder(order.number!!)

            assertThat(result).isNotNull()
            assertThat(result.status).isEqualTo(OrderStatus.CANCELLED)
        }

        @Test
        fun `cancelOrder should throw an exception for a COMPLETED order`() {
            val order = createOrder(status = OrderStatus.COMPLETED)

            every { orderRepository.findByOrderNumber(any()) } returns order

            assertThatThrownBy { orderService.cancelOrder(order.number!!) }
                .isInstanceOf(SelfOrderManagementException::class.java)
                .hasFieldOrPropertyWithValue("errorType", ErrorType.INVALID_ORDER_STATE_TRANSITION)
        }
    }
}
