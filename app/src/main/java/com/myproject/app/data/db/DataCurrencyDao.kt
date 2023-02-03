package com.myproject.app.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DataCurrencyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCurrency(listCurrency: List<DataCurrency>): LongArray

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrency(listCurrency: DataCurrency): Long

    @Query("select * from DataCurrency")
    suspend fun selectAll(): List<DataCurrency>

    @Query("select count(*) from DataCurrency")
    suspend fun getCountCurrency(): Int

    @Query("select * from DataCurrency where currency = :currency")
    suspend fun selectCurrencyByCurrency(currency: String): DataCurrency

    @Query("delete from DataCurrency where id = :id")
    suspend fun deleteCurrency(id: Int): Int

    @Query("delete from DataCurrency where currency = :currency")
    suspend fun deleteCurrencyByCurrency(currency: String): Int

}