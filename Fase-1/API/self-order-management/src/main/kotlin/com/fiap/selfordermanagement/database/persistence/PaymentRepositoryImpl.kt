package com.fiap.selfordermanagement.database.persistence

import com.fiap.selfordermanagement.application.adapter.repository.PaymentRepository
import com.fiap.selfordermanagement.application.domain.entities.Payment
import com.fiap.selfordermanagement.application.domain.errors.ErrorType
import com.fiap.selfordermanagement.application.domain.errors.SelfOrderManagementException
import com.fiap.selfordermanagement.database.persistence.jpa.PaymentJpaRepository
import com.fiap.selfordermanagement.database.persistence.mapper.PaymentMapper
import org.mapstruct.factory.Mappers

class PaymentRepositoryImpl(
    private val paymentJpaRepository: PaymentJpaRepository,
) : PaymentRepository {
    private val mapper = Mappers.getMapper(PaymentMapper::class.java)

    override fun findByOrderNumber(orderNumber: Long): Payment? {
        return paymentJpaRepository.findById(orderNumber)
            .map(mapper::toDomain)
            .orElse(null)
    }

    override fun findAll(): List<Payment> {
        return paymentJpaRepository.findAll()
            .map(mapper::toDomain)
    }

    override fun create(payment: Payment): Payment {
        payment.orderNumber.let {
            findByOrderNumber(it)?.let {
                throw SelfOrderManagementException(
                    errorType = ErrorType.PAYMENT_ALREADY_EXISTS,
                    message = "Payment record for order [${payment.orderNumber}] already exists",
                )
            }
        }
        return persist(payment)
    }

    override fun update(payment: Payment): Payment {
        val newItem =
            payment.orderNumber.let { findByOrderNumber(it)?.update(payment) }
                ?: throw SelfOrderManagementException(
                    errorType = ErrorType.PAYMENT_NOT_FOUND,
                    message = "Payment record for order [${payment.orderNumber}] not found",
                )
        return persist(newItem)
    }

    private fun persist(payment: Payment): Payment =
        payment
            .let(mapper::toEntity)
            .let(paymentJpaRepository::save)
            .let(mapper::toDomain)
}
