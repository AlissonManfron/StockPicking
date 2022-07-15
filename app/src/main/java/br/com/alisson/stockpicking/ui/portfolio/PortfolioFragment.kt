package br.com.alisson.stockpicking.ui.portfolio

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.alisson.stockpicking.R
import br.com.alisson.stockpicking.data.adapter.StockListAdapter
import br.com.alisson.stockpicking.data.db.AppDatabase
import br.com.alisson.stockpicking.data.model.Stock
import br.com.alisson.stockpicking.data.repository.StockDataSource
import br.com.alisson.stockpicking.databinding.FragmentPortfolioBinding

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
                stockRepository = StockDataSource(dataBase.stockDao())
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
        setupRecyclerView()

        viewModel.getStocks().observe(viewLifecycleOwner) {
            it.valuedList?.let { stocks ->
                adapter.setStocks(stocks)
                showListStocks(true)
            }
            it.emptyList?.let {
                showListStocks(false)
            }
        }

        viewModel.getAllStocks()

        return view
    }

    private fun showListStocks(show: Boolean) {
        activity?.let {
            binding.emptyView.imageView.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.ic_baseline_format_list_bulleted_24
                )
            )
        }
        binding.emptyView.clEmptyList.visibility = if (!show) View.VISIBLE else View.GONE
        binding.recycler.visibility = if (show) View.VISIBLE else View.GONE
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(requireContext())
        binding.recycler.layoutManager = layoutManager
        adapter = StockListAdapter(emptyList(), itemOnClick)
        binding.recycler.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}