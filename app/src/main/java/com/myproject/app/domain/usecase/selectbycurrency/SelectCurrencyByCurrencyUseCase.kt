package com.myproject.app.domain.usecase.selectbycurrency

import com.myproject.app.data.db.DataCurrency

interface SelectCurrencyByCurrencyUseCase {

    suspend fun selectCurrencyByCurrency(name: String): DataCurrency

}