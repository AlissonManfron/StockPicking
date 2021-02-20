package br.com.alisson.stockpicking.ui.resume

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import br.com.alisson.stockpicking.R

class ResumeFragment : Fragment() {

    private lateinit var viewModel: ResumeViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        viewModel =
                ViewModelProviders.of(this).get(ResumeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_resume, container, false)
        val textView: TextView = root.findViewById(R.id.text_dashboard)
        viewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}