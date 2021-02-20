package br.com.alisson.stockpicking.data.db.dao

import androidx.room.*
import br.com.alisson.stockpicking.data.db.StockEntity

@Dao
interface StockDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(stock: StockEntity)

    @Query("SELECT * FROM stock")
    suspend fun getAll(): List<StockEntity>

    @Query("SELECT * FROM stock WHERE id = :id")
    suspend fun getStock(id: Long): StockEntity

    @Delete
    suspend fun delete(stock: StockEntity)

}