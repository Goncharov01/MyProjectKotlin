package com.myproject.app.domain.usecase.getcurrency

import com.myproject.app.data.db.DataCurrency

interface GetCurrencyUseCase {

    suspend fun getCurrency(): List<DataCurrency>

}