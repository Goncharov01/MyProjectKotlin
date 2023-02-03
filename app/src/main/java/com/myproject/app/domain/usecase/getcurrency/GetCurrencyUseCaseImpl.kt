package com.myproject.app.domain.usecase.getcurrency

import com.myproject.app.data.DataJsonRepository
import com.myproject.app.data.db.DataCurrency

class GetCurrencyUseCaseImpl(val dataJsonRepository: DataJsonRepository) : GetCurrencyUseCase {

    override suspend fun getCurrency(): List<DataCurrency> = dataJsonRepository.getCurrency()

}