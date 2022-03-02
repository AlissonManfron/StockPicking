package br.com.alisson.stockpicking.data.repository

import br.com.alisson.stockpicking.data.db.dao.StockDao
import br.com.alisson.stockpicking.data.db.toStockEntity
import br.com.alisson.stockpicking.data.db.toStockList
import br.com.alisson.stockpicking.data.model.Stock

class StockRepositoryImpl(
    private val stockDao: StockDao
) : StockRepository {

    override suspend fun createStock(stock: Stock) {
        val stockEntity = stock.toStockEntity()
        stockDao.save(stockEntity)
    }

    override suspend fun deleteStock(stock: Stock) {
        stockDao.delete(stock.toStockEntity())
    }

    override suspend fun getStocks(): List<Stock> {
        val list = stockDao.getAll()
        return list.toStockList()
    }
}