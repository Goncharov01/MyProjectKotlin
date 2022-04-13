package com.myproject.app.domain.usecase.deleteCurrency

interface DeleteCurrencyUseCase {

    suspend fun deleteCurrency(id: Int): Int

}