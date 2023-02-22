package com.myproject.app.data.network

import com.myproject.app.domain.DataJson
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyApi {

    @GET("latest?access_key=3f909da0b4c6066d48d117b792784b2e")
    suspend fun getCurrency(): DataJson

    @GET("latest?access_key=3f909da0b4c6066d48d117b792784b2e")
    suspend fun getCurrencyPage(@Query("page") page: Int): Response<DataJson>

    companion object {

        private const val baseUrl: String = "http://data.fixer.io/api/"

        fun getApi(): CurrencyApi = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CurrencyApi::class.java)

    }
}