package com.example.mojepocasi_utb

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application): AndroidViewModel(application) {

    private val repository = ProtoRepository(application)

    val data = repository.readProto.asLiveData()

    fun updateValue(posledniVyhledavani: String, vyhledavaniHistorie: String) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateValue(posledniVyhledavani, vyhledavaniHistorie)
    }
}