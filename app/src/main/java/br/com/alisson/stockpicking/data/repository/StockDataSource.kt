package br.com.alisson.stockpicking.data.repository

import br.com.alisson.stockpicking.data.db.dao.StockDao
import br.com.alisson.stockpicking.data.db.toStock
import br.com.alisson.stockpicking.data.db.toStockEntity
import br.com.alisson.stockpicking.data.db.toStockList
import br.com.alisson.stockpicking.data.model.Stock
import kotlin.math.roundToInt

class StockDataSource(
    private val stockDao: StockDao
) : StockRepository {
    override suspend fun getStockByTicker(ticker: String): Stock? {
        val stockByTicker = stockDao.getStockByTicker(ticker)
        return stockByTicker?.toStock() ?: return null
    }

    override suspend fun createStock(stock: Stock) {
        val searchStock = getStockByTicker(stock.ticker)
        if (searchStock != null) {
            updateStock(searchStock, stock)
        } else {
            stockDao.save(stock.toStockEntity()).also { calculateWeight() }
        }
    }

    override suspend fun updateStock(stockOld: Stock, stockNew: Stock) {
        val totalBalance = stockOld.getTotalBalance()
        val totalApport = stockNew.quantity * stockNew.price
        stockOld.price = (totalBalance + totalApport) / (stockOld.quantity + stockNew.quantity)
        stockOld.quantity += stockNew.quantity
        stockDao.save(stockOld.toStockEntity()).also {  calculateWeight() }
    }

    override suspend fun deleteStock(stock: Stock) {
        stockDao.delete(stock.toStockEntity()).also { calculateWeight() }
    }

    override suspend fun getStocks(): List<Stock> {
        val list = stockDao.getAll()
        return list.toStockList()
    }

    private suspend fun calculateWeight() {
        var weight: Double
        val stocks = getStocks()

        var sum = 0.0
        stocks.forEach { stock ->
            sum += stock.getTotalBalance()
        }

        stocks.forEach { stock ->
            weight = (stock.getTotalBalance() / sum) * 100
            stock.weight = weight.roundToInt()
            stockDao.save(stock.toStockEntity())
        }
    }
}