package br.com.alisson.stockpicking.ui.portfolio

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.alisson.stockpicking.R
import br.com.alisson.stockpicking.data.adapter.StockListAdapter
import br.com.alisson.stockpicking.data.db.AppDatabase
import br.com.alisson.stockpicking.data.model.Stock
import br.com.alisson.stockpicking.data.repository.StockDbDataSource
import br.com.alisson.stockpicking.databinding.FragmentPortfolioBinding
import br.com.alisson.stockpicking.infrastructure.util.StateScreen
import br.com.alisson.stockpicking.infrastructure.util.StateUpdate

class PortfolioFragment : Fragment() {
    private lateinit var adapter: StockListAdapter
    private var _binding: FragmentPortfolioBinding? = null
    private val binding get() = _binding!!

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
    ): View {
        _binding = FragmentPortfolioBinding.inflate(inflater, container, false)
        val view = binding.root
        setupRecyclerView(view)

        viewModel.getStocks().observe(viewLifecycleOwner, {
            it.let {
                adapter.setStocks(it)
            }
        })

        viewModel.getUpdate().observe(viewLifecycleOwner, {
            if (it == StateUpdate.UPDATED)
                viewModel.getAllStocks()
        })

        viewModel.getScreenWithItem().observe(viewLifecycleOwner, {
            if (it == StateScreen.WITH_ITEM) {
                binding.clEmptyList.visibility = View.GONE
                binding.recycler.visibility = View.VISIBLE
            }
        })

        viewModel.getScreenEmpty().observe(viewLifecycleOwner, {
            if (it == StateScreen.EMPTY) {
                binding.clEmptyList.visibility = View.VISIBLE
                binding.recycler.visibility = View.GONE
            }
        })

        viewModel.getAllStocks()

        return view
    }

    private fun setupRecyclerView(root: View) {
        val recyclerView = root.findViewById<RecyclerView>(R.id.recycler)
        val layoutManager = LinearLayoutManager(requireContext())
        recyclerView.layoutManager = layoutManager
        adapter = StockListAdapter(emptyList(), itemOnClick)
        recyclerView.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}