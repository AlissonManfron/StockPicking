package br.com.alisson.stockpicking.data.model

import java.util.*

data class Stock(
    val id: Int?,
    val ticker: String,
    var weight: Int,
    val quantity: Int,
    val price: Double,
    val date: Date
) {
    fun getCurrentBalance(): String {
        return String.format("R$ %.2f", (quantity * price))
    }

    fun getTotalBalance(): Double = quantity * price

    fun getWeight() = String.format("%d", weight) + "%"
}