package com.myproject.app.domain.usecase.deletecurrencybycurrency

import com.myproject.app.data.DataJsonRepository

class DeleteCurrencyByCurrencyUseCaseImpl(val dataJsonRepository: DataJsonRepository) :
    DeleteCurrencyByCurrencyUseCase {

    override suspend fun deleteCurrencyByCurrency(currency: String): Int {
        return dataJsonRepository.deleteCurrencyByCurrency(currency)
    }

}