package com.myproject.app.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface CurrencyApi {

    @GET("latest?access_key=3f909da0b4c6066d48d117b792784b2e")
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