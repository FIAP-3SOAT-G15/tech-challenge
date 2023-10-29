package com.fiap.selfordermanagement.adapters.driver.configuration

import com.fiap.selfordermanagement.SelfOrderManagementApplication
import com.fiap.selfordermanagement.application.ports.incoming.*
import com.fiap.selfordermanagement.application.ports.outgoing.*
import com.fiap.selfordermanagement.application.services.*
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
        inputRepository: InputRepository,
    ): ProductService {
        return ProductService(productRepository, inputRepository)
    }

    @Bean
    fun createStockService(
        productRepository: ProductRepository,
        inputRepository: InputRepository,
    ): StockService {
        return StockService(productRepository, inputRepository)
    }

    @Bean
    fun createOrderService(
        orderRepository: OrderRepository,
        loadCustomerUseCase: LoadCustomerUseCase,
        loadProductsUseCase: LoadProductUseCase,
        loadStockUseCase: LoadStockUseCase,
        adjustInventoryUseCase: AdjustInventoryUseCase,
        loadPaymentUseCase: LoadPaymentUseCase,
        providePaymentRequestUseCase: ProvidePaymentRequestUseCase,
        syncPaymentStatusUseCase: SyncPaymentStatusUseCase,
    ): OrderService {
        return OrderService(
            orderRepository,
            loadCustomerUseCase,
            loadProductsUseCase,
            loadStockUseCase,
            adjustInventoryUseCase,
            loadPaymentUseCase,
            providePaymentRequestUseCase,
            syncPaymentStatusUseCase,
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
