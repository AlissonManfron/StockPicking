package br.com.alisson.stockpicking.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.alisson.stockpicking.data.model.Stock

@Entity(tableName = "stock")
data class StockEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int,
    val ticker: String,
    val weight: Int
)

fun Stock.toStockEntity() : StockEntity {
    return with(this) {
        StockEntity(
            id = this.id ?: 0,
            ticker = this.ticker,
            weight = this.weight
        )
    }
}

fun StockEntity.toStock() : Stock {
    return with(this) {
        Stock(
            id = this.id,
            ticker = this.ticker,
            weight = this.weight
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

        return stocks

    }
}