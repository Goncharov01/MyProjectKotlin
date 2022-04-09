package com.myproject.app.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized

@Database(entities = [DataCurrency::class], version = 4)
abstract class DataBaseDataCurrency : RoomDatabase() {
    abstract fun dataCurrencyDao(): DataCurrencyDao
}

object DataBaseBuilder {
    private var instans: DataBaseDataCurrency? = null

    @OptIn(InternalCoroutinesApi::class)
    fun getInstans(context: Context): DataBaseDataCurrency {
        if (instans == null) {
            synchronized(DataBaseDataCurrency::class.java) {
                instans = Room.databaseBuilder(
                    context.applicationContext,
                    DataBaseDataCurrency::class.java,
                    "DataBaseDataCurrency"
                ).build()
            }
        }
        return instans!!
    }

}



