package com.example.address.api

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


class RetrofitHelper {
    companion object {
        val BASE_URL = "https://brasilapi.com.br/"
    }

    private fun buildRetrofit() = Retrofit.Builder()
        .baseUrl(Companion.BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    fun addressApi() : AddressApi = buildRetrofit().create(AddressApi::class.java)

}