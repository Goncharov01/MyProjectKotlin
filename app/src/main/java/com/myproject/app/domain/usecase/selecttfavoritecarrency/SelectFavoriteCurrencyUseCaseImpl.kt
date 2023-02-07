package com.myproject.app.domain.usecase.selecttfavoritecarrency

import com.myproject.app.data.DataJsonRepository
import com.myproject.app.data.db.DataCurrency

class SelectFavoriteCurrencyUseCaseImpl(val dataJsonRepository: DataJsonRepository)
    : SelectFavoriteCurrencyUseCase
{

    override suspend fun selectFavoriteCurrency(): MutableList<DataCurrency> {
        return dataJsonRepository.selectFavoriteCurrency()
    }

}