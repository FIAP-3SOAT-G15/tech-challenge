package com.fiap.selfordermanagement.application.adapter.controller.configuration

import com.fiap.selfordermanagement.SelfOrderManagementApplication
import com.fiap.selfordermanagement.application.adapter.repository.ComponentRepository
import com.fiap.selfordermanagement.application.adapter.repository.CustomerRepository
import com.fiap.selfordermanagement.application.adapter.repository.OrderRepository
import com.fiap.selfordermanagement.application.adapter.repository.PaymentProvider
import com.fiap.selfordermanagement.application.adapter.repository.PaymentRepository
import com.fiap.selfordermanagement.application.adapter.repository.ProductRepository
import com.fiap.selfordermanagement.application.adapter.repository.StockRepository
import com.fiap.selfordermanagement.application.adapter.repository.TransactionalRepository
import com.fiap.selfordermanagement.application.adapter.services.ComponentService
import com.fiap.selfordermanagement.application.adapter.services.CustomerService
import com.fiap.selfordermanagement.application.adapter.services.OrderService
import com.fiap.selfordermanagement.application.adapter.services.PaymentService
import com.fiap.selfordermanagement.application.adapter.services.ProductService
import com.fiap.selfordermanagement.application.adapter.services.StockService
import com.fiap.selfordermanagement.application.usecases.LoadComponentUseCase
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@ComponentScan(basePackageClasses = [SelfOrderManagementApplication::class])
class ServiceConfig {
    @Bean
    fun createCustomerService(customerRepository: CustomerRepository): CustomerService {
        return CustomerService(customerRepository)
    }

    @Bean
    fun createProductService(
        productRepository: ProductRepository,
        loadComponentUseCase: LoadComponentUseCase,
    ): ProductService {
        return ProductService(
            productRepository,
            loadComponentUseCase,
        )
    }

    @Bean
    fun createComponentService(
        componentRepository: ComponentRepository,
        stockRepository: StockRepository,
        productRepository: ProductRepository,
    ): ComponentService {
        return ComponentService(
            componentRepository,
            stockRepository,
            productRepository,
        )
    }

    @Bean
    fun createStockService(stockRepository: StockRepository): StockService {
        return StockService(stockRepository)
    }

    @Bean
    fun createOrderService(
        orderRepository: OrderRepository,
        loadCustomerUseCase: com.fiap.selfordermanagement.application.usecases.LoadCustomerUseCase,
        loadProductsUseCase: com.fiap.selfordermanagement.application.usecases.LoadProductUseCase,
        adjustInventoryUseCase: com.fiap.selfordermanagement.application.usecases.AdjustStockUseCase,
        loadPaymentUseCase: com.fiap.selfordermanagement.application.usecases.LoadPaymentUseCase,
        providePaymentRequestUseCase: com.fiap.selfordermanagement.application.usecases.ProvidePaymentRequestUseCase,
        syncPaymentStatusUseCase: com.fiap.selfordermanagement.application.usecases.SyncPaymentStatusUseCase,
        transactionalRepository: TransactionalRepository,
    ): OrderService {
        return OrderService(
            orderRepository,
            loadCustomerUseCase,
            loadProductsUseCase,
            adjustInventoryUseCase,
            loadPaymentUseCase,
            providePaymentRequestUseCase,
            syncPaymentStatusUseCase,
            transactionalRepository,
        )
    }

    @Bean
    fun createPaymentService(
        paymentRepository: PaymentRepository,
        paymentProvider: PaymentProvider,
    ): PaymentService {
        return PaymentService(
            paymentRepository,
            paymentProvider,
        )
    }
}
