package com.mariannecunha.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mariannecunha.core.livedata.SingleEventLiveData
import com.mariannecunha.domain.usecase.FetchCategories
import com.mariannecunha.domain.usecase.GetWords
import com.mariannecunha.domain.usecase.SaveWords
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchViewModel(
    private val fetchCategories: FetchCategories,
    private val getWords: GetWords,
    private val saveWords: SaveWords
) : ViewModel() {

    private val _categoriesLiveData = MutableLiveData<MutableList<String>>()
    val categoriesLiveData: LiveData<MutableList<String>> = _categoriesLiveData

    private val _clickSearchLiveData = SingleEventLiveData<Unit>()
    val clickSearchLiveData: LiveData<Unit> = _clickSearchLiveData

    fun getCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            val categories = fetchCategories()
            _categoriesLiveData.postValue(categories)
        }
    }

    fun onSearchClick() {
        _clickSearchLiveData.postValue(Unit)
    }

    fun getWordList(): MutableList<String> {
        return getWords()
    }

    fun saveWords(searchText: String) {
        saveWords.invoke(searchText)
    }
}
