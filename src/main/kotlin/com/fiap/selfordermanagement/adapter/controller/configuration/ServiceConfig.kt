package com.fiap.selfordermanagement.adapter.controller.configuration

import com.fiap.selfordermanagement.SelfOrderManagementApplication
import com.fiap.selfordermanagement.adapter.gateway.ComponentGateway
import com.fiap.selfordermanagement.adapter.gateway.CustomerGateway
import com.fiap.selfordermanagement.adapter.gateway.OrderGateway
import com.fiap.selfordermanagement.adapter.gateway.PaymentGateway
import com.fiap.selfordermanagement.adapter.gateway.PaymentProviderGateway
import com.fiap.selfordermanagement.adapter.gateway.ProductGateway
import com.fiap.selfordermanagement.adapter.gateway.StockGateway
import com.fiap.selfordermanagement.adapter.gateway.TransactionalGateway
import com.fiap.selfordermanagement.usecases.AdjustStockUseCase
import com.fiap.selfordermanagement.usecases.ConfirmOrderUseCase
import com.fiap.selfordermanagement.usecases.LoadComponentUseCase
import com.fiap.selfordermanagement.usecases.LoadCustomerUseCase
import com.fiap.selfordermanagement.usecases.LoadPaymentUseCase
import com.fiap.selfordermanagement.usecases.LoadProductUseCase
import com.fiap.selfordermanagement.usecases.ProvidePaymentRequestUseCase
import com.fiap.selfordermanagement.usecases.services.ComponentService
import com.fiap.selfordermanagement.usecases.services.CustomerService
import com.fiap.selfordermanagement.usecases.services.PaymentSyncService
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
        loadPaymentUseCase: LoadPaymentUseCase,
        transactionalRepository: TransactionalGateway,
    ): OrderService {
        return OrderService(
            orderRepository,
            loadCustomerUseCase,
            loadProductsUseCase,
            adjustInventoryUseCase,
            providePaymentRequestUseCase,
            loadPaymentUseCase,
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
            paymentProvider
        )
    }
    
    @Bean
    fun paymentSyncService(
        confirmOrderUseCase: ConfirmOrderUseCase,
        loadPaymentUseCase: LoadPaymentUseCase,
        paymentGateway: PaymentGateway,
        paymentProvider: PaymentProviderGateway,
    ): PaymentSyncService {
        return PaymentSyncService(
            confirmOrderUseCase,
            loadPaymentUseCase,
            paymentGateway,
            paymentProvider,
        )
    }
}
