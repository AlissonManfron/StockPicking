package br.com.alisson.stockpicking.ui.resume

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import br.com.alisson.stockpicking.R
import br.com.alisson.stockpicking.data.db.AppDatabase
import br.com.alisson.stockpicking.data.repository.StockDbDataSource
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.MPPointF


class ResumeFragment : Fragment() {

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
    ): View? {

        val root = inflater.inflate(R.layout.fragment_resume, container, false)

        val pieChartView = root.findViewById<PieChart>(R.id.pieChartView)

        viewModel.getEntryList().observe(viewLifecycleOwner, Observer { pieList ->

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

            pieChartView.data = pieData

            pieChartView.setDrawEntryLabels(true)
            pieChartView.setUsePercentValues(true)
            pieChartView.description.isEnabled = false
            pieChartView.centerText = "Seu Portifólio"
            pieChartView.setExtraOffsets(5F, 10F, 5F, 15F)
            pieChartView.setCenterTextSize(16F)
            pieChartView.centerTextRadiusPercent = 50F
            pieChartView.animateY(1400, Easing.EaseInOutQuad)

            pieChartView.invalidate()
        })

        viewModel.getAllStocks()


        return root
    }

}