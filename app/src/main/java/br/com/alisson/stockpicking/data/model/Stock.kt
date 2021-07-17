package br.com.alisson.stockpicking.data.model

import java.util.*

data class Stock(
    val id: Int?,
    val ticker: String,
    val weight: Int,
    val quantity: Int,
    val price: Double,
    val date: Date
)