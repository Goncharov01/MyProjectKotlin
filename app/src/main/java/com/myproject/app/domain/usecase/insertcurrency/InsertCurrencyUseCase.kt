package com.myproject.app.domain.usecase.insertcurrency

import com.myproject.app.data.db.DataCurrency

interface InsertCurrencyUseCase {

    suspend fun insertCurrency(listCurrency: DataCurrency): Long

}