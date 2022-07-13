package com.myproject.app.domain.usecase.selectbycurrency

import com.myproject.app.data.DataJsonRepository
import com.myproject.app.data.db.DataCurrency

class SelectCurrencyByCurrencyUseCaseImpl(val dataJsonRepository: DataJsonRepository) :
    SelectCurrencyByCurrencyUseCase {

    override suspend fun selectCurrencyByCurrency(currency: String): DataCurrency {
        return dataJsonRepository.selectCurrencyByCurrency(currency)
    }

}