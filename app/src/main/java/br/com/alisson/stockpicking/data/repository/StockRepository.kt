package br.com.alisson.stockpicking.data.repository

import br.com.alisson.stockpicking.data.model.Stock

interface StockRepository {

    suspend fun getStockByTicker(ticker: String): Stock?

    suspend fun createStock(stock: Stock)

    suspend fun updateStock(stockOld: Stock, stockNew: Stock)

    suspend fun deleteStock(stock: Stock)

    suspend fun getStocks(): List<Stock>
}