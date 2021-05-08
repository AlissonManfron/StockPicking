package br.com.alisson.stockpicking.ui.alert

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import br.com.alisson.stockpicking.R

class AlertFragment : Fragment() {

    private lateinit var viewModel: AlertViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(AlertViewModel::class.java)

        return inflater.inflate(R.layout.fragment_alert, container, false)
    }
}