package br.com.alisson.stockpicking.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import br.com.alisson.stockpicking.MainActivity
import br.com.alisson.stockpicking.R
import br.com.alisson.stockpicking.data.model.Stock
import br.com.alisson.stockpicking.infrastructure.DialogUtils


interface StockOnclickListener {
    fun onclickListener(stock: Stock)
}

class HomeFragment : Fragment(), MainActivity.OnButtonClickListener {

    val homeViewModel: HomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        (activity as MainActivity?)?.setOnButtonClickListener(this@HomeFragment)

        return root
    }

    private fun showPopup() {
        DialogUtils.showPopupAddSctock(requireActivity(), object : StockOnclickListener {
            override fun onclickListener(stock: Stock) {

            }
        })
    }

    override fun onClick() {
        showPopup()
    }

}