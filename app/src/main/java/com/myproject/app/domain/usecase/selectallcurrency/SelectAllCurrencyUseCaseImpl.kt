package com.myproject.app.domain.usecase.selectallcurrency

import com.myproject.app.data.DataJsonRepository
import com.myproject.app.data.db.DataCurrency

class SelectAllCurrencyUseCaseImpl(val dataJsonRepository: DataJsonRepository) :
    SelectAllCurrencyUseCase {

    override suspend fun selectAll(): List<DataCurrency> {
        return dataJsonRepository.selectAll()
    }

}