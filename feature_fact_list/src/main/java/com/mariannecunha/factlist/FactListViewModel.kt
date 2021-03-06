package com.mariannecunha.factlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.kittinunf.result.coroutines.SuspendableResult
import com.mariannecunha.domain.model.Fact
import com.mariannecunha.domain.model.GlobalFacts
import com.mariannecunha.domain.usecase.FetchFacts
import com.mariannecunha.domain.usecase.GetWords
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class FactListViewModel(private val fetchFacts: FetchFacts, private val getWords: GetWords) : ViewModel() {

    private val _successLiveData = MutableLiveData<List<Fact>>()
    val successLiveData: LiveData<List<Fact>> = _successLiveData
    private val _emptySuccessLiveData = MutableLiveData<Unit>()
    val emptySuccessLiveData: LiveData<Unit> = _emptySuccessLiveData
    private val _errorLiveData = MutableLiveData<Unit>()
    val errorLiveData: LiveData<Unit> = _errorLiveData
    private val _welcomeLiveData = MutableLiveData<Unit>()
    val welcomeLiveData: LiveData<Unit> = _welcomeLiveData

    fun getFacts() {
        viewModelScope.launch(Dispatchers.IO) {
            val query = getWords.invoke().first()
            if (query.isEmpty()) {
                _welcomeLiveData.postValue(Unit)
                return@launch
            }
            val response = fetchFacts(query)
            handleResponse(response)
        }
    }

    private fun handleResponse(response: SuspendableResult<GlobalFacts, Exception>) {
        val isAnError = response.component2() != null
        val isAnEmptySuccess = response.component1()?.result?.isEmpty() == true

        when {
            isAnError -> _errorLiveData.postValue(Unit)
            isAnEmptySuccess -> _emptySuccessLiveData.postValue(Unit)
            else -> _successLiveData.postValue(response.component1()?.result)
        }
    }
}
