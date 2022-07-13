package com.myproject.app.data

import android.content.Context
import com.myproject.app.data.db.DataBaseBuilder
import com.myproject.app.data.db.DataCurrency
import com.myproject.app.data.network.CurrencyApi
import com.myproject.app.domain.DataJson

data class DataJsonRepository(var context: Context) {

    private val dataCurrency = DataBaseBuilder.getInstans(context).dataCurrencyDao()

    suspend fun getCurrency(): DataJson {
        return CurrencyApi.getApi().getCurrency()
    }

    suspend fun insertCurrency(listCurrency: DataCurrency): Long {
        return dataCurrency.insertCurrency(listCurrency)
    }

    suspend fun selectAll(): List<DataCurrency> {
        return dataCurrency.selectAll()
    }

    suspend fun selectCurrencyByCurrency(currency: String): DataCurrency {
        return dataCurrency.selectCurrencyByCurrency(currency)
    }

    suspend fun deleteCurrency(id: Int): Int {
        return dataCurrency.deleteCurrency(id)
    }

    suspend fun deleteCurrencyByCurrency(currency: String): Int {
        return dataCurrency.deleteCurrencyByCurrency(currency)
    }

}