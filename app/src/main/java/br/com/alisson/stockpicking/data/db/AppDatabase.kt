package br.com.alisson.stockpicking.data.db

import android.content.Context
import androidx.room.*
import br.com.alisson.stockpicking.data.db.dao.StockDao

@Database(entities = [StockEntity::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun stockDao(): StockDao

    companion object {
        fun createDataBase(context: Context): StockDao {
            return Room
                .databaseBuilder(context, AppDatabase::class.java, "stocks.db")
                .build()
                .stockDao()

        }
    }
}