package br.com.alisson.stockpicking.ui.portfolio

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import br.com.alisson.stockpicking.data.model.ResourceStock
import br.com.alisson.stockpicking.data.model.Stock
import br.com.alisson.stockpicking.data.repository.StockRepository
import br.com.alisson.stockpicking.infrastructure.util.StateScreen
import kotlinx.coroutines.launch

class PortfolioViewModel(private val stockRepository: StockRepository) : ViewModel() {

    private val stocks = MutableLiveData<ResourceStock<List<Stock>?>>()

    fun getStocks() = stocks

    init {
        getAllStocks()
    }

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
                stocks.value = ResourceStock(null, StateScreen.EMPTY.name)
            } else {
                stocks.value = ResourceStock(list)
            }
        }
    }

    class PortfolioViewModelFactory(private val stockRepository: StockRepository) :
        ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return PortfolioViewModel(stockRepository) as T
        }
    }

}