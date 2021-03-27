package br.com.alisson.stockpicking

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import br.com.alisson.stockpicking.data.db.AppDatabase
import br.com.alisson.stockpicking.data.model.Stock
import br.com.alisson.stockpicking.data.repository.StockDbDataSource
import br.com.alisson.stockpicking.infrastructure.`interface`.StockOnclickListener
import br.com.alisson.stockpicking.infrastructure.util.DialogUtils
import br.com.alisson.stockpicking.ui.portfolio.PortfolioViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    private val viewModel: PortfolioViewModel by viewModels(
        factoryProducer = {
            val dataBase = AppDatabase.getDatabase(this)
            PortfolioViewModel.PortfolioViewModelFactory(
                stockRepository = StockDbDataSource(dataBase.stockDao())
            )
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        getSupportActionBar()?.hide()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.bottom_navigation_main)

        navController = findNavController(R.id.nav_host_fragment)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_resume,
                R.id.navigation_portfolio,
                R.id.navigation_alert,
                R.id.navigation_account
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        fab_add.setOnClickListener {
            showPopup()
        }
    }

    private fun showPopup() {
        DialogUtils.showPopupAddSctock(this, object : StockOnclickListener {
            override fun onclickListener(stock: Stock) {
                viewModel.createStock(stock)
                navController.navigate(R.id.navigation_resume)
            }
        })
    }

}