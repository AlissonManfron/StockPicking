package br.com.alisson.stockpicking.ui.alert

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import br.com.alisson.stockpicking.R
import br.com.alisson.stockpicking.ui.account.AccountViewModel

class AlertFragment : Fragment() {

    private lateinit var viewModel: AlertViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel =
            ViewModelProviders.of(this).get(AlertViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_alert, container, false)
        val textView: TextView = root.findViewById(R.id.text_alert)
        viewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}