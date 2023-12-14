package com.example.address.service

import com.example.address.api.AddressApi
import com.example.address.domain.Address

class AddressService(
    private val addressApi: AddressApi,
) {

    fun findAddressByCep(cep: String): Address? {
        return addressApi.findAddressByCep(cep)
            .execute()
            .body()?.let { addressDto ->
                addressDto.convertToAddress
            }
    }

}