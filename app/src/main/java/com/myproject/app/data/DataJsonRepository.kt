package com.myproject.app.data

import android.content.Context
import com.myproject.app.data.db.DataBaseBuilder
import com.myproject.app.data.db.DataCurrency
import com.myproject.app.data.network.CurrencyApi

data class DataJsonRepository(var context: Context) {

    private val dataBaseCurrency = DataBaseBuilder.getInstans(context).dataCurrencyDao()

    suspend fun getCurrency(): List<DataCurrency> {
        val countCurrency = dataBaseCurrency.getCountCurrency()

        if (countCurrency > 0)
            return dataBaseCurrency.selectAll()
        else {
            val listDataCurrency = mutableListOf<DataCurrency>()
            val dataCurrency = CurrencyApi.getApi().getCurrency()

            for (entry in dataCurrency.rates) {
                listDataCurrency.add(
                    DataCurrency(
                        currency = entry.key,
                        value = entry.value.toString()
                    )
                )
            }
            dataBaseCurrency.insertAllCurrency(listDataCurrency)

            return listDataCurrency
        }
    }

    suspend fun selectFavoriteCurrency(): MutableList<DataCurrency> {
        return dataBaseCurrency.selectFavoriteCurrency()
    }

    suspend fun insertCurrency(listCurrency: DataCurrency): Long {
        return dataBaseCurrency.insertCurrency(listCurrency)
    }

    suspend fun selectAll(): List<DataCurrency> {
        return dataBaseCurrency.selectAll()
    }

    suspend fun selectCurrencyByCurrency(currency: String): DataCurrency {
        return dataBaseCurrency.selectCurrencyByCurrency(currency)
    }

    suspend fun deleteCurrency(id: Int): Int {
        return dataBaseCurrency.deleteCurrency(id)
    }

    suspend fun deleteCurrencyByCurrency(currency: String): Int {
        return dataBaseCurrency.deleteCurrencyByCurrency(currency)
    }

}