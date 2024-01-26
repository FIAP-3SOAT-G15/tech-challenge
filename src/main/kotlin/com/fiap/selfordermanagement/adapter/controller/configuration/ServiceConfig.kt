package com.fiap.selfordermanagement.adapter.controller.configuration

import com.fiap.selfordermanagement.SelfOrderManagementApplication
import com.fiap.selfordermanagement.adapter.gateway.*
import com.fiap.selfordermanagement.usecases.*
import com.fiap.selfordermanagement.usecases.services.ComponentService
import com.fiap.selfordermanagement.usecases.services.CustomerService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import services.OrderService
import services.PaymentService
import services.ProductService
import services.StockService

@Configuration
@ComponentScan(basePackageClasses = [SelfOrderManagementApplication::class])
class ServiceConfig {
    @Bean
    fun createCustomerService(customerRepository: CustomerGateway): CustomerService {
        return CustomerService(customerRepository)
    }

    @Bean
    fun createProductService(
        productRepository: ProductGateway,
        loadComponentUseCase: LoadComponentUseCase,
    ): ProductService {
        return ProductService(
            productRepository,
            loadComponentUseCase,
        )
    }

    @Bean
    fun createComponentService(
        componentRepository: ComponentGateway,
        stockRepository: StockGateway,
        productRepository: ProductGateway,
    ): ComponentService {
        return ComponentService(
            componentRepository,
            stockRepository,
            productRepository,
        )
    }

    @Bean
    fun createStockService(stockRepository: StockGateway): StockService {
        return StockService(stockRepository)
    }

    @Bean
    fun createOrderService(
        orderRepository: OrderGateway,
        loadCustomerUseCase: LoadCustomerUseCase,
        loadProductsUseCase: LoadProductUseCase,
        adjustInventoryUseCase: AdjustStockUseCase,
        providePaymentRequestUseCase: ProvidePaymentRequestUseCase,
        loadProductUseCase: LoadProductUseCase,
        transactionalRepository: TransactionalGateway,
    ): OrderService {
        return OrderService(
            orderRepository,
            loadCustomerUseCase,
            loadProductsUseCase,
            adjustInventoryUseCase,
            providePaymentRequestUseCase,
            loadProductUseCase,
            transactionalRepository,
        )
    }

    @Bean
    fun createPaymentService(
        paymentRepository: PaymentGateway,
        paymentProvider: PaymentProviderGateway,
    ): PaymentService {
        return PaymentService(
            paymentRepository,
            paymentProvider,
        )
    }
}
