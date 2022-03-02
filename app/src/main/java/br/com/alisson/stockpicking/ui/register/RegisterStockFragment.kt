package br.com.alisson.stockpicking.ui.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import br.com.alisson.stockpicking.R
import br.com.alisson.stockpicking.databinding.RegisterStockFragmentBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel

class RegisterStockFragment : Fragment() {

    private val viewModel: RegisterStockViewModel by viewModel()
    private var _binding: RegisterStockFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = RegisterStockFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.lifecycleOwner = this
        binding.viewmodel = viewModel

        viewModel.getStock().observe(viewLifecycleOwner) { resource ->
            resource?.data?.let {
                goToPortfolio()
            }
            resource?.error?.let {
                showToast(it)
            }
        }

        binding.stockSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                viewModel.weight.value = progress
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })

        return view

    }

    private fun goToPortfolio() {
        lifecycleScope.launch {
            delay(500)
            requireActivity().findNavController(R.id.nav_host_fragment).navigate(R.id.navigation_resume)
        }
    }

    private fun showToast(msg: Int) {
        Toast.makeText(
            activity,
            msg,
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.clear()
    }

}