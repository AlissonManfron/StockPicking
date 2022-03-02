package br.com.alisson.stockpicking.ui.portfolio

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.alisson.stockpicking.R
import br.com.alisson.stockpicking.data.adapter.StockListAdapter
import br.com.alisson.stockpicking.data.model.Stock
import br.com.alisson.stockpicking.databinding.FragmentPortfolioBinding
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class PortfolioFragment : Fragment() {

    private val viewModel: PortfolioViewModel by viewModel()
    private val adapter: StockListAdapter by inject()
    private var _binding: FragmentPortfolioBinding? = null
    private val binding get() = _binding!!

    private val itemOnClick: (stock: Stock) -> Unit = { stock ->
        viewModel.deleteStock(stock)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPortfolioBinding.inflate(inflater, container, false)
        val view = binding.root
        setupRecyclerView()

        viewModel.getStocks().observe(viewLifecycleOwner, {
            it.valuedList?.let { stocks ->
                adapter.setStocks(stocks)
                showListStocks(true)
            }
            it.emptyList?.let {
                showListStocks(false)
            }
        })

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
        adapter.setListener(itemOnClick)
        binding.recycler.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}