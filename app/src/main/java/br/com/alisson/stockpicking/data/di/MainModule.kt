package br.com.alisson.stockpicking.data.di

import br.com.alisson.stockpicking.data.db.AppDatabase
import br.com.alisson.stockpicking.data.repository.StockRepositoryImpl
import br.com.alisson.stockpicking.data.repository.StockRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module


val mainModule = module {
    single { AppDatabase.createDataBase(androidContext()) }
    factory<StockRepository> { StockRepositoryImpl(get()) }
}