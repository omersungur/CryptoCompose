package com.omersungur.cryptocompose.viewmodel

import androidx.lifecycle.ViewModel
import com.omersungur.cryptocompose.model.Crypto
import com.omersungur.cryptocompose.repo.CryptoRepository
import com.omersungur.cryptocompose.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CryptoDetailViewModel @Inject constructor(
    private val repository: CryptoRepository
) : ViewModel() {

    suspend fun getCrypto(id: String): Resource<Crypto> {
        return repository.getCrypto()
    }
}