package com.myproject.app.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.myproject.app.recycler.DataCurrency

@Dao
interface DataCurrencyDao {

    @Insert()
    suspend fun insertCurrency(listCurrency: DataCurrency): Long

    @Query("select * from DataCurrency")
    suspend fun selectAll(): List<DataCurrency>

    @Query("delete from DataCurrency where id = :id")
    suspend fun deleteCurrency(id: Int): Int

}