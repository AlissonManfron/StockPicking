package br.com.alisson.stockpicking.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import br.com.alisson.stockpicking.R
import com.anychart.AnyChart
import com.anychart.AnyChartView
import com.anychart.charts.Pie
import com.anychart.enums.Align
import com.anychart.enums.LegendLayout

class HomeFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        val homeViewModel: HomeViewModel by activityViewModels()

        val pie = AnyChart.pie()

        val anyChartView: AnyChartView = root.findViewById(R.id.any_chart_view)
        anyChartView.setProgressBar(root.findViewById(R.id.progress_bar))

        initPie(pie, anyChartView)

        homeViewModel.getData().observe(viewLifecycleOwner, Observer {
            it.let {
                pie.data(it)
            }
        })

        homeViewModel.getOnclick().observe(viewLifecycleOwner, Observer {
            showPopup()
        })

        return root
    }

    private fun initPie(pie: Pie, anyChartView: AnyChartView) {

        pie.title("Distribuição correta da sua carteira")

        pie.labels().position("outside")

        pie.legend().title().enabled(true)
        pie.legend().title()
            .text("Ações")
            .padding(0.0, 0.0, 10.0, 0.0)

        pie.legend()
            .position("center-bottom")
            .itemsLayout(LegendLayout.HORIZONTAL)
            .align(Align.CENTER)

        anyChartView.setBackgroundColor(requireActivity().getColor(R.color.white) )
        anyChartView.setChart(pie)
    }

    private fun showPopup() {
        val builder = AlertDialog.Builder(requireActivity())
        builder.setMessage("Teste")
            .setPositiveButton("OK"
            ) { _, _ ->

            }
            .setNegativeButton("CANCEL"
            ) { _, _ ->

            }

        val alert = builder.create()
        alert.setTitle("Test")
        alert.show()
    }
}