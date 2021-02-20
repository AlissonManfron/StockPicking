package br.com.alisson.stockpicking.infrastructure.`interface`

import br.com.alisson.stockpicking.data.model.Stock

interface StockOnclickListener {
    fun onclickListener(stock: Stock)
}