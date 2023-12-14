package com.example.address.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface AddressApi {

    @GET(value = "api/cep/v1/{cep}")
    fun findAddressByCep(@Path("cep") cep: String): Call<AddressDTO>

}

