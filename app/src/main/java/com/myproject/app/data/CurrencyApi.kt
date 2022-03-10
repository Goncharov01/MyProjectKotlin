package com.myproject.app.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface CurrencyApi {

    @GET("latest?access_key=c997dc5205c78685ff31f3b4c18db686")
    suspend fun getCurrency(): DataJson

    companion object {

        private val baseUrl: String = "http://data.fixer.io/api/"

        fun getApi(): CurrencyApi = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CurrencyApi::class.java)

    }
}