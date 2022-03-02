package br.com.alisson.stockpicking

import android.app.Application
import br.com.alisson.stockpicking.data.di.mainModule
import br.com.alisson.stockpicking.ui.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MyApplication)
            modules(mainModule + presentationModule)
        }
    }
}