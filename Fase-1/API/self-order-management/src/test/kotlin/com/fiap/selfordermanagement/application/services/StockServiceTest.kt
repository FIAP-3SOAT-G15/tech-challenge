package com.fiap.selfordermanagement.application.services

import com.fiap.selfordermanagement.application.domain.errors.ErrorType
import com.fiap.selfordermanagement.application.domain.errors.SelfOrderManagementException
import com.fiap.selfordermanagement.application.ports.outgoing.StockRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class StockServiceTest {
    private val stockRepository = mockk<StockRepository>()

    private val stockService =
        StockService(
            stockRepository,
        )

    @AfterEach
    fun tearDown() {
        unmockkAll()
    }

    @Nested
    inner class GetByProductNumberTest {
        @Test
        fun `getByProductNumber should return a Stock when it exists`() {
            val stock = createStock()

            every { stockRepository.findByProductNumber(stock.productNumber!!) } returns stock

            val result = stockService.getByProductNumber(stock.productNumber!!)

            assertThat(result).isEqualTo(stock)
        }

        @Test
        fun `getByProductNumber should throw an exception when the stock is not found`() {
            val productNumber = 123L

            every { stockRepository.findByProductNumber(productNumber) } returns null

            assertThatThrownBy { stockService.getByProductNumber(productNumber) }
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

            every { stockRepository.findByProductNumber(stock.productNumber!!) } returns stock
            every { stockRepository.upsert(any()) } answers { firstArg() }

            val result = stockService.increment(stock.productNumber!!, incrementQuantity)

            assertThat(result).isNotNull()
            assertThat(result.productNumber).isEqualTo(stock.productNumber)
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

            every { stockRepository.findByProductNumber(stock.productNumber!!) } returns stock
            every { stockRepository.upsert(any()) } answers { firstArg() }

            val result = stockService.decrement(stock.productNumber!!, decrementQuantity)

            assertThat(result).isNotNull()
            assertThat(result.productNumber).isEqualTo(stock.productNumber)
            assertThat(result.quantity).isEqualTo(initialQuantity - decrementQuantity)
        }

        @Test
        fun `decrement should throw an exception for insufficient stock`() {
            val initialQuantity = 100L
            val decrementQuantity = 100L
            val stock = createStock(quantity = initialQuantity)

            every { stockRepository.findByProductNumber(stock.productNumber!!) } returns stock

            assertThatThrownBy { stockService.decrement(stock.productNumber!!, decrementQuantity) }
                .isInstanceOf(SelfOrderManagementException::class.java)
                .hasFieldOrPropertyWithValue("errorType", ErrorType.INSUFFICIENT_STOCK)
        }
    }
}
