package br.com.alisson.stockpicking.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.alisson.stockpicking.MainActivity
import br.com.alisson.stockpicking.R
import br.com.alisson.stockpicking.data.db.AppDatabase
import br.com.alisson.stockpicking.data.db.toStockList
import br.com.alisson.stockpicking.data.model.Stock
import br.com.alisson.stockpicking.data.repository.StockDbDataSource
import br.com.alisson.stockpicking.infrastructure.DialogUtils
import kotlinx.android.synthetic.main.fragment_home.*


interface StockOnclickListener {
    fun onclickListener(stock: Stock)
}

class HomeFragment : Fragment(), MainActivity.OnButtonClickListener {
    private lateinit var adapter: StockListAdapter

    val itemOnClick: (stock: Stock) -> Unit = { stock ->
        homeViewModel.deleteStock(stock)
    }

    private val homeViewModel: HomeViewModel by activityViewModels(
        factoryProducer = {
            val dataBase = AppDatabase.getDatabase(requireContext())

            HomeViewModel.HomeViewModelFactory(
                stockRepository = StockDbDataSource(dataBase.stockDao())
            )
        }
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        (activity as MainActivity?)?.setOnButtonClickListener(this@HomeFragment)

        setupRecyclerView(root)

        homeViewModel.getStocks().observe(viewLifecycleOwner, Observer {
            it.let {
                adapter.setStocks(it)
            }
        })

        homeViewModel.getUpdate().observe(viewLifecycleOwner, Observer {
            homeViewModel.getAllStocks()
        })

        homeViewModel.getAllStocks()

        return root
    }

    private fun setupRecyclerView(root: View) {
        val recyclerView = root.findViewById<RecyclerView>(R.id.recycler)
        val layoutManager = LinearLayoutManager(requireContext())
        recyclerView.layoutManager = layoutManager
        adapter = StockListAdapter(emptyList(), requireContext(), itemOnClick)
        recyclerView.adapter = adapter
    }

    private fun showPopup() {
        DialogUtils.showPopupAddSctock(requireActivity(), object : StockOnclickListener {
            override fun onclickListener(stock: Stock) {
                homeViewModel.createStock(stock)
            }
        })
    }

    override fun onClick() {
        showPopup()
    }

}