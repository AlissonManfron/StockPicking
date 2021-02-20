package br.com.alisson.stockpicking.ui.portfolio

import androidx.lifecycle.*
import br.com.alisson.stockpicking.data.model.Stock
import br.com.alisson.stockpicking.data.repository.StockRepository
import br.com.alisson.stockpicking.infrastructure.util.StateScreen
import br.com.alisson.stockpicking.infrastructure.util.StateUpdate
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PortfolioViewModel(private val stockRepository: StockRepository) : ViewModel() {

    private val stocks = MutableLiveData<List<Stock>>()
    private val updated = MutableLiveData<StateUpdate>()
    private val screenEmpty = MutableLiveData<StateScreen>()
    private val screenWithItem = MutableLiveData<StateScreen>()

    fun getStocks() : LiveData<List<Stock>> {
        return stocks
    }

    fun getUpdate() : LiveData<StateUpdate> {
        return updated
    }

    fun getScreenEmpty() : LiveData<StateScreen> {
        return screenEmpty
    }

    fun getScreenWithItem() : LiveData<StateScreen> {
        return screenWithItem
    }

    fun createStock(stock: Stock) {
        viewModelScope.launch {
            stockRepository.createStock(stock)
            delay(300)
            callUpdated()
        }
    }

    fun deleteStock(stock: Stock) {
        viewModelScope.launch {
            stockRepository.deleteStock(stock)
            delay(300)
            callUpdated()
        }
    }

    fun getAllStocks() {
        viewModelScope.launch {
            val list = stockRepository.getStocks()

            if (list.isEmpty()) {
                setScreenEmpty()
            } else {
                setScreenWithItem()
                stocks.value = list
            }
        }
    }

    private fun callUpdated() {
        updated.value = StateUpdate.UPDATED
        updated.value = StateUpdate.UNKNOW
    }

    private fun setScreenEmpty() {
        screenEmpty.value = StateScreen.EMPTY
        screenEmpty.value = StateScreen.UNKNOW
    }

    private fun setScreenWithItem() {
        screenWithItem.value = StateScreen.WITH_ITEM
        screenWithItem.value = StateScreen.UNKNOW
    }


    class PortfolioViewModelFactory(private val stockRepository: StockRepository) :
            ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return PortfolioViewModel(stockRepository) as T
        }
    }

}