package br.com.alisson.stockpicking.ui.register

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import br.com.alisson.stockpicking.R
import br.com.alisson.stockpicking.data.model.Resource
import br.com.alisson.stockpicking.data.model.Stock
import br.com.alisson.stockpicking.data.repository.StockRepository
import br.com.alisson.stockpicking.infrastructure.extensions.NumberExtensions.Companion.toWeight
import br.com.alisson.stockpicking.infrastructure.extensions.StringExtensions.Companion.toDate
import kotlinx.coroutines.launch

class RegisterStockViewModel(private val repository: StockRepository) : ViewModel() {

    private var stock = MutableLiveData<Resource<Stock>?>()

    fun getStock() = stock

    var ticker = MutableLiveData<String>()
    var quantity = MutableLiveData<String>()
    var price = MutableLiveData<String>()
    var date = MutableLiveData<String>()
    var weight = MutableLiveData<Int>()

    fun onClick(view: View?) {

        val ticker = ticker.value ?: ""
        val quantity = quantity.value?.toInt() ?: 0
        val price = price.value?.toDouble() ?: 0.0
        val date = date.value?.toDate()
        val weight = weight.value?.toWeight() ?: 0

        if (ticker.isEmpty()) {
            stock.value = Resource(null, R.string.msg_empty_field_ticker)
            return
        }

        if (quantity <= 0) {
            stock.value = Resource(null, R.string.msg_empty_field_quantity)
            return
        }

        if (price <= 0.0) {
            stock.value = Resource(null, R.string.msg_empty_field_price)
            return
        }

        if (date == null) {
            stock.value = Resource(null, R.string.msg_empty_field_date)
            return
        }

        if (weight <= 0) {
            stock.value = Resource(null, R.string.msg_empty_field_weight)
            return
        }

        viewModelScope.launch {
            val stockBean = Stock(id = null, ticker, weight, quantity, price, date)
            repository.createStock(stockBean)
            stock.value = Resource(stockBean)
        }
    }

    fun clear() {
        stock.value = null
        ticker = MutableLiveData<String>()
        quantity = MutableLiveData<String>()
        price = MutableLiveData<String>()
        date = MutableLiveData<String>()
        weight = MutableLiveData<Int>()
    }

    class RegisterStockViewModelFactory(private val stockRepository: StockRepository) :
        ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return RegisterStockViewModel(stockRepository) as T
        }
    }
}