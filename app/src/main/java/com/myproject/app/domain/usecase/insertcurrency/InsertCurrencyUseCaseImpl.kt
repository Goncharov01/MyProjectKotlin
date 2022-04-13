package com.myproject.app.domain.usecase.insertcurrency

import com.myproject.app.data.DataJsonRepository
import com.myproject.app.data.db.DataCurrency

class InsertCurrencyUseCaseImpl(val dataJsonRepository: DataJsonRepository) :
    InsertCurrencyUseCase {

    override suspend fun insertCurrency(listCurrency: DataCurrency): Long {
        return dataJsonRepository.insertCurrency(listCurrency)
    }

}