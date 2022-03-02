package br.com.alisson.stockpicking.ui.register

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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

        val stockBean = Stock(id = null, ticker, weight, quantity, price, date)

        viewModelScope.launch {
            repository.createStock(stockBean)
            calculateWeight(stockBean)
        }
        stock.value = Resource(stockBean)
    }

    private suspend fun calculateWeight(stockBean: Stock): Unit {
        var weight = 0.0
        val currentStockBalance = stockBean.getTotalBalance()

        val stocks = repository.getStocks()

        if (stocks.isEmpty()) {
            stockBean.weight = 100
            return
        }

        var sum = 0.0// = stockBean.getTotalBalance()
        stocks.forEach { stock ->
            sum += stock.getTotalBalance()
        }

        stocks.forEach { stock ->
            weight = (stock.getTotalBalance() / sum) * 100
            stock.weight = weight.toInt()
            repository.createStock(stock)
        }


        //stockBean.weight = weight.toInt()
    }

    fun clear() {
        stock.value = null
        ticker = MutableLiveData<String>()
        quantity = MutableLiveData<String>()
        price = MutableLiveData<String>()
        date = MutableLiveData<String>()
        weight = MutableLiveData<Int>()
    }
}