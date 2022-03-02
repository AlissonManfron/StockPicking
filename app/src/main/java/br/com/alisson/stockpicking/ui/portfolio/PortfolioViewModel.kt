package br.com.alisson.stockpicking.ui.portfolio

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.alisson.stockpicking.data.model.ResourceList
import br.com.alisson.stockpicking.data.model.Stock
import br.com.alisson.stockpicking.data.repository.StockRepository
import br.com.alisson.stockpicking.infrastructure.util.StateScreen
import kotlinx.coroutines.launch

class PortfolioViewModel(private val stockRepository: StockRepository) : ViewModel() {

    private val stocks = MutableLiveData<ResourceList<List<Stock>?>>()

    fun getStocks() = stocks

    fun createStock(stock: Stock) {
        viewModelScope.launch {
            stockRepository.createStock(stock)
            getAllStocks()
        }
    }

    fun deleteStock(stock: Stock) {
        viewModelScope.launch {
            stockRepository.deleteStock(stock)
            getAllStocks()
        }
    }

    fun getAllStocks() {
        viewModelScope.launch {
            val list = stockRepository.getStocks()
            if (list.isEmpty()) {
                stocks.value = ResourceList(null, StateScreen.EMPTY.name)
            } else {
                stocks.value = ResourceList(list)
            }
        }
    }
}