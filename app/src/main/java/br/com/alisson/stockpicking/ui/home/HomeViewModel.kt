package br.com.alisson.stockpicking.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry

class HomeViewModel : ViewModel() {

    private var data = MutableLiveData<MutableList<DataEntry>>()

    private var onclick = MutableLiveData<Boolean>()

    private var list: MutableList<DataEntry> = ArrayList()

    init {
        list.add(ValueDataEntry("ITSA4", 200))
        list.add(ValueDataEntry("VIVA3", 200))
        data.value = list
    }

    fun getData(): LiveData<MutableList<DataEntry>> {
        return data
    }

    fun getOnclick(): LiveData<Boolean> {
        return onclick
    }

    fun setOnClick() {
        list.add(ValueDataEntry("BBDC4", 200))
        data.value = list
        //onclick.value = true
    }


}