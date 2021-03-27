package br.com.alisson.stockpicking.data.repository

import br.com.alisson.stockpicking.data.model.Stock

interface StockRepository {

    suspend fun createStock(stock: Stock)

    suspend fun deleteStock(stock: Stock)

    suspend fun getStocks(): List<Stock>
}