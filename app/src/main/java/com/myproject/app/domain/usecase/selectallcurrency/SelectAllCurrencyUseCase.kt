package com.myproject.app.domain.usecase.selectallcurrency

import com.myproject.app.data.db.DataCurrency

interface SelectAllCurrencyUseCase {

    suspend fun selectAll(): List<DataCurrency>

}