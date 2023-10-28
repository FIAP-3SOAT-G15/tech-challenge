package com.fiap.selfordermanagement.adapters.driver.configuration

import com.fiap.selfordermanagement.SelfOrderManagementApplication
import com.fiap.selfordermanagement.application.ports.incoming.AdjustInventoryUseCase
import com.fiap.selfordermanagement.application.ports.incoming.LoadCustomerUseCase
import com.fiap.selfordermanagement.application.ports.incoming.LoadPaymentUseCase
import com.fiap.selfordermanagement.application.ports.incoming.LoadProductUseCase
import com.fiap.selfordermanagement.application.ports.incoming.LoadStockUseCase
import com.fiap.selfordermanagement.application.ports.incoming.ProvidePaymentRequestUseCase
import com.fiap.selfordermanagement.application.ports.incoming.SyncPaymentStatusUseCase
import com.fiap.selfordermanagement.application.ports.outgoing.CustomerRepository
import com.fiap.selfordermanagement.application.ports.outgoing.OrderRepository
import com.fiap.selfordermanagement.application.ports.outgoing.PaymentProvider
import com.fiap.selfordermanagement.application.ports.outgoing.PaymentRepository
import com.fiap.selfordermanagement.application.ports.outgoing.ProductRepository
import com.fiap.selfordermanagement.application.ports.outgoing.StockRepository
import com.fiap.selfordermanagement.application.services.CustomerService
import com.fiap.selfordermanagement.application.services.OrderService
import com.fiap.selfordermanagement.application.services.PaymentService
import com.fiap.selfordermanagement.application.services.ProductService
import com.fiap.selfordermanagement.application.services.StockService
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
    fun createProductService(productRepository: ProductRepository): ProductService {
        return ProductService(productRepository)
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
