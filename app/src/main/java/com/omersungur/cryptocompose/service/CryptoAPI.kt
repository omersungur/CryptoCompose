package com.omersungur.cryptocompose.service

import com.omersungur.cryptocompose.model.Crypto
import com.omersungur.cryptocompose.model.CryptoList
import retrofit2.http.GET

interface CryptoAPI {

    //https://raw.githubusercontent.com/atilsamancioglu/IA32-CryptoComposeData/main/crypto.json > Crypto
    //https://raw.githubusercontent.com/atilsamancioglu/K21-JSONDataSet/master/crypto.json > Crypto List

    @GET("atilsamancioglu/K21-JSONDataSet/master/crypto.json")
    suspend fun getCryptoList() : CryptoList

    @GET("atilsamancioglu/IA32-CryptoComposeData/main/crypto.json")
    suspend fun getCryptoDetail() : Crypto
}