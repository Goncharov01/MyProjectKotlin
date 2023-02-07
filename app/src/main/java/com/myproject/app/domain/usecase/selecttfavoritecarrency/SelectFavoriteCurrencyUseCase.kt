package com.myproject.app.domain.usecase.selecttfavoritecarrency

import com.myproject.app.data.db.DataCurrency

interface SelectFavoriteCurrencyUseCase {

    suspend fun selectFavoriteCurrency(): MutableList<DataCurrency>

}