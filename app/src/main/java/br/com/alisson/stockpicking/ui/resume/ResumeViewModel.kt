package br.com.alisson.stockpicking.ui.resume

import android.graphics.Color
import androidx.lifecycle.*
import br.com.alisson.stockpicking.data.repository.StockRepository
import com.github.mikephil.charting.data.PieEntry
import kotlinx.coroutines.launch


class ResumeViewModel(private val stockRepository: StockRepository) : ViewModel() {

    private val pieEntryList = MutableLiveData<List<PieEntry>>()

    fun getEntryList(): LiveData<List<PieEntry>> {
        return pieEntryList
    }

    fun getAllStocks() {
        viewModelScope.launch {
            val stocks = stockRepository.getStocks()

            val pieList = arrayListOf<PieEntry>()
            for (stock in stocks) {
                pieList.add(PieEntry(stock.weight.toFloat(), stock.ticker))
            }
            pieEntryList.value = pieList
        }
    }


    val colors = arrayListOf(
        Color.rgb(0, 0, 0),
        Color.rgb(0, 102, 255),
        Color.rgb(187, 194, 195),
        Color.rgb(153, 153, 153),
        Color.rgb(102, 153, 255),
        Color.rgb(0, 153, 204),
        Color.rgb(255, 22, 15),
        Color.rgb(51, 102, 204),
        Color.rgb(72, 150, 121),
        Color.rgb(163, 163, 117),
        Color.rgb(9, 202, 232),
        Color.rgb(253, 138, 91),
        Color.rgb(102, 102, 255),
        Color.rgb(236, 80, 134),
        Color.rgb(9, 237, 182),
        Color.rgb(204, 102, 153),
        Color.rgb(255, 71, 26),
        Color.rgb(255, 179, 179),
        Color.rgb(97, 212, 121)
    )

    class ResumeViewModelFactory(private val stockRepository: StockRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ResumeViewModel(stockRepository) as T
        }
    }
}