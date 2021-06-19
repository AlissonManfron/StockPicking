package br.com.alisson.stockpicking.ui.resume

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import br.com.alisson.stockpicking.data.db.AppDatabase
import br.com.alisson.stockpicking.data.repository.StockDbDataSource
import br.com.alisson.stockpicking.databinding.FragmentResumeBinding
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.MPPointF


class ResumeFragment : Fragment() {

    private var _binding: FragmentResumeBinding? = null
    private val binding get() = _binding!!


    private val viewModel: ResumeViewModel by activityViewModels(
        factoryProducer = {
            val dataBase = AppDatabase.getDatabase(requireContext())
            ResumeViewModel.ResumeViewModelFactory(
                stockRepository = StockDbDataSource(dataBase.stockDao())
            )
        }
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResumeBinding.inflate(inflater, container, false)
        val view = binding.root

        viewModel.getEntryList().observe(viewLifecycleOwner, { pieList ->

            val pieDataSet = PieDataSet(pieList, "")
            pieDataSet.setDrawIcons(false)
            pieDataSet.sliceSpace = 3f
            pieDataSet.iconsOffset = MPPointF(0F, 40F)
            pieDataSet.selectionShift = 5f
            pieDataSet.colors = viewModel.colors

            val pieData = PieData(pieDataSet)
            pieData.setValueFormatter(PercentFormatter())
            pieData.setValueTextSize(11f)
            pieData.setValueTextColor(Color.WHITE)

            binding.pieChartView.data = pieData

            binding.pieChartView.setDrawEntryLabels(true)
            binding.pieChartView.setUsePercentValues(true)
            binding.pieChartView.description.isEnabled = false
            binding.pieChartView.centerText = "Seu Portifólio"
            binding.pieChartView.setExtraOffsets(5F, 10F, 5F, 15F)
            binding.pieChartView.setCenterTextSize(15F)
            binding.pieChartView.centerTextRadiusPercent = 50F
            binding.pieChartView.animateY(1400, Easing.EaseInOutQuad)

            binding.pieChartView.invalidate()
        })

        viewModel.getAllStocks()

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}