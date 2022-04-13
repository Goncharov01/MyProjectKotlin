package com.myproject.app.domain.usecase.getcurrency

import com.myproject.app.domain.DataJson

interface GetCurrencyUseCase {

    suspend fun getCurrency(): DataJson

}