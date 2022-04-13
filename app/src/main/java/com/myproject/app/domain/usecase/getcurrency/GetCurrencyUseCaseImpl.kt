package com.myproject.app.domain.usecase.getcurrency

import com.myproject.app.data.DataJsonRepository
import com.myproject.app.domain.DataJson

class GetCurrencyUseCaseImpl(val dataJsonRepository: DataJsonRepository) : GetCurrencyUseCase {

    override suspend fun getCurrency(): DataJson = dataJsonRepository.getCurrency()

}