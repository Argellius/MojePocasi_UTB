package com.example.mojepocasi_utb

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application): AndroidViewModel(application) {

    private val repository = ProtoRepository(application)

    val firstName = repository.readProto.asLiveData()

    fun updateValue(firstName: String, lastName: String, age: Int) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateValue(firstName, lastName, age)
    }
}