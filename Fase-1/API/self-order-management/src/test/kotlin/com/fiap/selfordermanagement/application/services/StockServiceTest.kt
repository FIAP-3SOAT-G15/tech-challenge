package com.fiap.selfordermanagement.application.services

import com.fiap.selfordermanagement.adapter.gateway.StockGateway
import com.fiap.selfordermanagement.domain.errors.ErrorType
import com.fiap.selfordermanagement.domain.errors.SelfOrderManagementException
import createStock
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import services.StockService

class StockServiceTest {
    private val stockRepository = mockk<StockGateway>()

    private val stockService =
        StockService(
            stockRepository,
        )

    @AfterEach
    fun tearDown() {
        unmockkAll()
    }

    @Nested
    inner class GetByComponentNumberTest {
        @Test
        fun `getByComponentNumber should return a Stock when it exists`() {
            val stock = createStock()

            every { stockRepository.findByComponentNumber(stock.componentNumber) } returns stock

            val result = stockService.getByComponentNumber(stock.componentNumber)

            assertThat(result).isEqualTo(stock)
        }

        @Test
        fun `getByComponentNumber should throw an exception when the stock is not found`() {
            val inputNumber = 123L

            every { stockRepository.findByComponentNumber(inputNumber) } returns null

            assertThatThrownBy { stockService.getByComponentNumber(inputNumber) }
                .isInstanceOf(SelfOrderManagementException::class.java)
                .hasFieldOrPropertyWithValue("errorType", ErrorType.STOCK_NOT_FOUND)
        }
    }

    @Nested
    inner class IncrementTest {
        @Test
        fun `increment should increase the stock quantity for a given product number`() {
            val initialQuantity = 100L
            val incrementQuantity = 100L
            val stock = createStock(quantity = initialQuantity)

            every { stockRepository.findByComponentNumber(stock.componentNumber) } returns stock
            every { stockRepository.update(any()) } answers { firstArg() }

            val result = stockService.increment(stock.componentNumber, incrementQuantity)

            assertThat(result).isNotNull
            assertThat(result.componentNumber).isEqualTo(stock.componentNumber)
            assertThat(result.quantity).isEqualTo(initialQuantity + incrementQuantity)
        }
    }

    @Nested
    inner class DecrementTest {
        @Test
        fun `decrement should reduce the stock quantity for a given product number`() {
            val initialQuantity = 100L
            val decrementQuantity = 50L
            val stock = createStock(quantity = initialQuantity)

            every { stockRepository.findByComponentNumber(stock.componentNumber) } returns stock
            every { stockRepository.update(any()) } answers { firstArg() }

            val result = stockService.decrement(stock.componentNumber, decrementQuantity)

            assertThat(result).isNotNull
            assertThat(result.componentNumber).isEqualTo(stock.componentNumber)
            assertThat(result.quantity).isEqualTo(initialQuantity - decrementQuantity)
        }

        @Test
        fun `decrement should throw an exception for insufficient stock`() {
            val initialQuantity = 100L
            val decrementQuantity = 100L
            val stock = createStock(quantity = initialQuantity)

            every { stockRepository.findByComponentNumber(stock.componentNumber) } returns stock

            assertThatThrownBy { stockService.decrement(stock.componentNumber, decrementQuantity) }
                .isInstanceOf(SelfOrderManagementException::class.java)
                .hasFieldOrPropertyWithValue("errorType", ErrorType.INSUFFICIENT_STOCK)
        }
    }
}
