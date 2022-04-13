package com.myproject.app.domain.usecase.deleteCurrency

import com.myproject.app.data.DataJsonRepository

class DeleteCurrencyUseCaseImpl(val dataJsonRepository: DataJsonRepository) :
    DeleteCurrencyUseCase {

    override suspend fun deleteCurrency(id: Int): Int {
        return dataJsonRepository.deleteCurrency(id)
    }

}