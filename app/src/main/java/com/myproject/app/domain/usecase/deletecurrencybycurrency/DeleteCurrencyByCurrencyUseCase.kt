package com.myproject.app.domain.usecase.deletecurrencybycurrency

interface DeleteCurrencyByCurrencyUseCase {

    suspend fun deleteCurrencyByCurrency(currency: String): Int

}