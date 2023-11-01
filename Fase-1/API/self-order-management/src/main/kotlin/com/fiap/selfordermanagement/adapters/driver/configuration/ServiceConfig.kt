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
        loadCustomerUseCase: LoadCustomerUseCase,
        loadProductsUseCase: LoadProductUseCase,
        adjustInventoryUseCase: AdjustStockUseCase,
        loadPaymentUseCase: LoadPaymentUseCase,
        providePaymentRequestUseCase: ProvidePaymentRequestUseCase,
        syncPaymentStatusUseCase: SyncPaymentStatusUseCase,
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
