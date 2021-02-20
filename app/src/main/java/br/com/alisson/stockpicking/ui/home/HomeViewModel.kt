package br.com.alisson.stockpicking.ui.home

import androidx.lifecycle.*
import br.com.alisson.stockpicking.data.model.Stock
import br.com.alisson.stockpicking.data.repository.StockRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeViewModel(private val stockRepository: StockRepository) : ViewModel() {

    private val stocks = MutableLiveData<List<Stock>>()
    private val updated = MutableLiveData<Boolean>()

    fun getStocks() : LiveData<List<Stock>> {
        return stocks
    }

    fun getUpdate() : LiveData<Boolean> {
        return updated
    }

    fun createStock(stock: Stock) {
        viewModelScope.launch {
            stockRepository.createStock(stock)
            delay(300)
            updated.value = true
        }
    }

    fun deleteStock(stock: Stock) {
        viewModelScope.launch {
            stockRepository.deleteStock(stock)
            delay(300)
            updated.value = true
        }
    }

    fun getAllStocks() {
        viewModelScope.launch {
            stocks.value = stockRepository.getStocks()
        }
    }


    class HomeViewModelFactory(private val stockRepository: StockRepository) :
            ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return HomeViewModel(stockRepository) as T
        }
    }

}