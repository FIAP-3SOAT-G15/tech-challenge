package com.fiap.selfordermanagement.application.services

import com.fiap.selfordermanagement.adapters.driver.web.request.OrderItemRequest
import com.fiap.selfordermanagement.application.domain.entities.Customer
import com.fiap.selfordermanagement.application.domain.entities.Input
import com.fiap.selfordermanagement.application.domain.entities.Order
import com.fiap.selfordermanagement.application.domain.entities.OrderItem
import com.fiap.selfordermanagement.application.domain.entities.Payment
import com.fiap.selfordermanagement.application.domain.entities.PaymentRequest
import com.fiap.selfordermanagement.application.domain.entities.Product
import com.fiap.selfordermanagement.application.domain.entities.Stock
import com.fiap.selfordermanagement.application.domain.valueobjects.OrderStatus
import com.fiap.selfordermanagement.application.domain.valueobjects.PaymentStatus
import com.fiap.selfordermanagement.application.domain.valueobjects.ProductType
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

fun createCustomer(
    document: String = "444.555.666-77",
    name: String = "Fulano de Tal",
    email: String = "fulano@detal.com",
    phone: String = "5511999999999",
    address: String = "São Paulo",
) = Customer(
    document = document,
    name = name,
    email = email,
    phone = phone,
    address = address,
)

fun createProduct(
    number: Long = 123,
    name: String = "Big Mac",
    type: ProductType = ProductType.MAIN,
    price: BigDecimal = BigDecimal("50.00"),
    description: String = "Dois hamburgueres, alface, queijo, molho especial, cebola, picles, num pão com gergelim",
    category: String = "Hamburger / Carne",
    minSub: Int = 3,
    maxSub: Int = 3,
    subItem: List<Product> = listOf(),
    inputs: List<Input> = listOf(),
) = Product(
    number = number,
    name = name,
    type = type,
    price = price,
    description = description,
    category = category,
    minSub = minSub,
    maxSub = maxSub,
    subItem = subItem,
    inputs = inputs,
)

fun createStock(
    productNumber: Long = 123,
    quantity: Long = 100,
) = Stock(
    inputNumber = productNumber,
    quantity = quantity,
)

fun createInput(
    inputNumber: Long = 123,
    name: String = "Lata refrigerante coca-cola 355ml",
    stock: Stock = createStock(),
) = Input(
    number = inputNumber,
    name = name,
    stock = stock,
)

fun createOrder(
    number: Long? = 98765,
    date: LocalDate = LocalDate.parse("2023-10-01"),
    customerNickname: String = "Fulano",
    customer: Customer? = null,
    status: OrderStatus = OrderStatus.CREATED,
    items: List<OrderItem> = listOf(createOrderItem()),
    total: BigDecimal = BigDecimal("50.00"),
) = Order(
    number = number,
    date = date,
    customerNickname = customerNickname,
    customer = customer,
    status = status,
    items = items,
    total = total,
)

fun createOrderItem(
    productNumber: Long = 123,
    quantity: Long = 1,
    price: BigDecimal = BigDecimal("50.00"),
) = OrderItem(
    productNumber = productNumber,
    quantity = quantity,
    price = price,
)

fun createOrderItemRequest(
    productNumber: Long = 123,
    quantity: Long = 1,
) = OrderItemRequest(
    productNumber = productNumber,
    quantity = quantity,
)

fun createPayment(
    orderNumber: Long = 98765,
    externalId: UUID = UUID.fromString("66b0f5f7-9997-4f49-a203-3dab2d936b50"),
    createdAt: LocalDateTime = LocalDateTime.parse("2023-10-01T18:00:00"),
    status: PaymentStatus = PaymentStatus.PENDING,
    statusChangedAt: LocalDateTime = LocalDateTime.parse("2023-10-01T18:00:00"),
) = Payment(
    orderNumber = orderNumber,
    externalId = externalId,
    createdAt = createdAt,
    status = status,
    statusChangedAt,
)

fun createPaymentRequest(
    externalId: UUID = UUID.fromString("66b0f5f7-9997-4f49-a203-3dab2d936b50"),
    qrCode: String = "U8OpcmlvPw==",
) = PaymentRequest(
    externalId = externalId,
    qrCode = qrCode,
)
