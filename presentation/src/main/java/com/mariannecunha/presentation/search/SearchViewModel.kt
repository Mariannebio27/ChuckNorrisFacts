package com.mariannecunha.presentation.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mariannecunha.domain.usecase.FetchCategories
import com.mariannecunha.domain.usecase.GetWords
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchViewModel(
    private val fetchCategories: FetchCategories,
    private val getWords: GetWords
) : ViewModel() {

    private val _categoriesLiveData = MutableLiveData<MutableList<String>>()
    val categoriesLiveData: LiveData<MutableList<String>> = _categoriesLiveData

    fun getCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            val categories = fetchCategories()
            _categoriesLiveData.postValue(categories)
        }
    }

    fun getWordList(): MutableList<String> {
        return getWords()
    }
}
