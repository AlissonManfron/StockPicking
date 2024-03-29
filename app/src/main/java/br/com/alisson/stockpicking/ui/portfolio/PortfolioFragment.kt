package br.com.alisson.stockpicking.ui.portfolio

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.alisson.stockpicking.R
import br.com.alisson.stockpicking.data.adapter.StockListAdapter
import br.com.alisson.stockpicking.data.db.AppDatabase
import br.com.alisson.stockpicking.data.model.Stock
import br.com.alisson.stockpicking.data.repository.StockDbDataSource
import br.com.alisson.stockpicking.infrastructure.util.StateScreen
import br.com.alisson.stockpicking.infrastructure.util.StateUpdate
import kotlinx.android.synthetic.main.fragment_portfolio.*

class PortfolioFragment : Fragment() {
    private lateinit var adapter: StockListAdapter

    private val itemOnClick: (stock: Stock) -> Unit = { stock ->
        viewModel.deleteStock(stock)
    }

    private val viewModel: PortfolioViewModel by activityViewModels(
        factoryProducer = {
            val dataBase = AppDatabase.getDatabase(requireContext())

            PortfolioViewModel.PortfolioViewModelFactory(
                stockRepository = StockDbDataSource(dataBase.stockDao())
            )
        }
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_portfolio, container, false)

        setupRecyclerView(root)

        viewModel.getStocks().observe(viewLifecycleOwner, Observer {
            it.let {
                adapter.setStocks(it)
            }
        })

        viewModel.getUpdate().observe(viewLifecycleOwner, Observer {
            if (it == StateUpdate.UPDATED)
                viewModel.getAllStocks()
        })

        viewModel.getScreenWithItem().observe(viewLifecycleOwner, Observer {
            if (it == StateScreen.WITH_ITEM) {
                cl_empty_list.visibility = View.GONE
                recycler.visibility = View.VISIBLE
            }
        })

        viewModel.getScreenEmpty().observe(viewLifecycleOwner, Observer {
            if (it == StateScreen.EMPTY) {
                cl_empty_list.visibility = View.VISIBLE
                recycler.visibility = View.GONE
            }
        })

        viewModel.getAllStocks()

        return root
    }

    private fun setupRecyclerView(root: View) {
        val recyclerView = root.findViewById<RecyclerView>(R.id.recycler)
        val layoutManager = LinearLayoutManager(requireContext())
        recyclerView.layoutManager = layoutManager
        adapter = StockListAdapter(emptyList(), requireContext(), itemOnClick)
        recyclerView.adapter = adapter
    }
}