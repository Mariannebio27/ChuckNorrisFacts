package com.mariannecunha.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WelcomeViewModel: ViewModel() {

    private val _clickLiveData = MutableLiveData<Unit>()
    val clickLiveData: LiveData<Unit> = _clickLiveData

    fun onWelcomeClick() {
        _clickLiveData.postValue(Unit)
    }
}