package com.mariannecunha.presentation.di

import com.mariannecunha.core.livedata.SingleEventLiveData
import com.mariannecunha.domain.usecase.FetchCategories
import com.mariannecunha.domain.usecase.FetchFacts
import com.mariannecunha.domain.usecase.GetWords
import com.mariannecunha.presentation.factlist.FactListAdapter
import com.mariannecunha.presentation.factlist.FactListViewModel
import com.mariannecunha.presentation.search.SearchListAdapter
import com.mariannecunha.presentation.search.SearchViewModel
import com.mariannecunha.presentation.search.SearchedWordsListAdapter
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {

    viewModel {
        FactListViewModel(
            get<FetchFacts>()
        )
    }

    viewModel {
        SearchViewModel(
            get<FetchCategories>(),
            get<GetWords>()
        )
    }

    factory {
        FactListAdapter()
    }

    factory { (liveData: SingleEventLiveData<String>) ->
        SearchListAdapter(liveData)
    }

    factory { (liveData: SingleEventLiveData<String>) ->
        SearchedWordsListAdapter(liveData)
    }
}
