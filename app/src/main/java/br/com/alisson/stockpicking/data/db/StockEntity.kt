package br.com.alisson.stockpicking.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.alisson.stockpicking.data.model.Stock
import java.util.*

@Entity(tableName = "stock")
data class StockEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int,
    val ticker: String,
    val weight: Int,
    val quantity: Int,
    val price: Double,
    val date: Date
)

fun Stock.toStockEntity() : StockEntity {
    return with(this) {
        StockEntity(
            id = this.id ?: 0,
            ticker = this.ticker,
            weight = this.weight,
            quantity = this.quantity,
            price = this.price,
            date = this.date
        )
    }
}

fun StockEntity.toStock() : Stock {
    return with(this) {
        Stock(
            id = this.id,
            ticker = this.ticker,
            weight = this.weight,
            quantity = this.quantity,
            price = this.price,
            date = this.date
        )
    }
}

fun List<StockEntity>.toStockList(): List<Stock> {
    return with(this) {
        val stocks = mutableListOf<Stock>()

        this.toList().forEach {
            val st = it.toStock()
            stocks += st
        }
        stocks
    }
}