package com.omersungur.cryptocompose.repo

import com.omersungur.cryptocompose.model.Crypto
import com.omersungur.cryptocompose.model.CryptoList
import com.omersungur.cryptocompose.service.CryptoAPI
import com.omersungur.cryptocompose.util.Resource
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class CryptoRepository @Inject constructor(
    private val api: CryptoAPI,
) {

    suspend fun getCryptoList() : Resource<CryptoList> {
        val response = try {
            api.getCryptoList()
        }
        catch (e: java.lang.Exception) {
            return Resource.Error("Error!")
        }
        return Resource.Success(response)
    }

    suspend fun getCrypto() : Resource<Crypto> {
        val response = try {
            api.getCryptoDetail()
        }
        catch (e: java.lang.Exception) {
            return Resource.Error("Error!")
        }
        return Resource.Success(response)
    }
}