package br.com.alisson.stockpicking.ui.di

import br.com.alisson.stockpicking.data.adapter.StockListAdapter
import br.com.alisson.stockpicking.ui.alert.AlertViewModel
import br.com.alisson.stockpicking.ui.portfolio.PortfolioViewModel
import br.com.alisson.stockpicking.ui.register.RegisterStockViewModel
import br.com.alisson.stockpicking.ui.resume.ResumeViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel { ResumeViewModel(get()) }
    viewModel { PortfolioViewModel(get()) }
    viewModel { RegisterStockViewModel(get()) }
    viewModel { AlertViewModel() }

    factory { StockListAdapter() }
}