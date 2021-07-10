package br.com.alisson.stockpicking.ui.resume

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import br.com.alisson.stockpicking.data.model.ResourceList
import br.com.alisson.stockpicking.data.repository.StockRepository
import br.com.alisson.stockpicking.infrastructure.util.StateScreen
import com.github.mikephil.charting.data.PieEntry
import kotlinx.coroutines.launch

class ResumeViewModel(private val stockRepository: StockRepository) : ViewModel() {

    private val pieEntryList = MutableLiveData<ResourceList<List<PieEntry>?>>()

    fun getEntryList() = pieEntryList

    fun getAllStocks() {
        viewModelScope.launch {
            val stocks = stockRepository.getStocks()

            if (stocks.isEmpty()) {
                pieEntryList.value = ResourceList(null, StateScreen.EMPTY.name)
            } else {
                val pieList = arrayListOf<PieEntry>()
                for (stock in stocks) {
                    pieList.add(PieEntry(stock.weight.toFloat(), stock.ticker))
                }
                pieEntryList.value = ResourceList(pieList)
            }
        }
    }

    class ResumeViewModelFactory(private val stockRepository: StockRepository) :
        ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ResumeViewModel(stockRepository) as T
        }
    }
}