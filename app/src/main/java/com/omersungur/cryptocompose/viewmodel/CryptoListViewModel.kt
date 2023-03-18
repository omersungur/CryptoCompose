package com.omersungur.cryptocompose.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omersungur.cryptocompose.model.CryptoListItem
import com.omersungur.cryptocompose.repo.CryptoRepository
import com.omersungur.cryptocompose.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class CryptoListViewModel @Inject constructor(
    private val repository: CryptoRepository
) : ViewModel(){

    var cryptoList = mutableStateOf<List<CryptoListItem>>(listOf())
    var errorMessage = mutableStateOf("")
    var isLoading = mutableStateOf(true)

    private var initialCryptoList = listOf<CryptoListItem>() // Verileri indirdikten sonra kullanıcı arama textini silerse yeniden verileri yüklememek için listeyi başka değişkende tutuyoruz.
    private var isSearchStarting = true

    init {
        loadCryptos()
    }

    fun searchCryptoList(query : String) {
        val listToSearch = if(isSearchStarting) {
            cryptoList.value
            //arama başladıysa internetten çektiğimiz verilerden gösteriyoruz.
        }
        else {
            // arama işlemi bittiyse listeyi kaydettiğimiz yerden tekrar çekiyoruz(internetten indirmiyoruz)
            initialCryptoList
        }

        viewModelScope.launch(Dispatchers.Default) {
            if(query.isEmpty()) {
                //arama yapıldıktan sonra edittext içi silinmişse bu blok çalışır.
                cryptoList.value = initialCryptoList
                isSearchStarting = true
                return@launch
            }

            val results = listToSearch.filter {
            // arama yaptığımız karakterlerle listede eşleşen varsa filtreleme yapıyoruz.
                it.currency!!.contains(query.trim(),ignoreCase = true)
            }

            if(isSearchStarting) {
                // bir arama başlatıldıysa bu blok çalışıyor
                //internetten çektiğimiz bütün listeyi başka bir değişkene atıyoruz
                initialCryptoList = cryptoList.value
                isSearchStarting = false
            }

            cryptoList.value = results
        }
    }

    fun loadCryptos() {
        viewModelScope.launch {
            when(val result = repository.getCryptoList()) {
                is Resource.Success -> {
                    val cryptoItems = result.data!!.map {item ->
                        // CryptoList'i CryptoItems'a dönüştürdük.
                        CryptoListItem(item.currency, item.price)
                    } as List<CryptoListItem>

                    errorMessage.value = ""
                    isLoading.value = false
                    cryptoList.value += cryptoItems
                }

                is Resource.Error -> {
                    errorMessage.value = result.message!!
                    isLoading.value = false
                }
                is Resource.Loading -> {
                    isLoading.value = true
                }
            }
        }
    }
}