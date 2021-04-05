package com.mariannecunha.search.di

import com.mariannecunha.core.livedata.SingleEventLiveData
import com.mariannecunha.domain.usecase.FetchCategories
import com.mariannecunha.domain.usecase.GetWords
import com.mariannecunha.search.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val searchModule = module {

    viewModel {
        SearchViewModel(
            get<FetchCategories>(),
            get<GetWords>()
        )
    }

    factory { (onSearchClick: (String) -> (Unit)) ->
        com.mariannecunha.search.SearchListAdapter(onSearchClick)
    }

    factory { (onSearchClick: (String) -> (Unit)) ->
        com.mariannecunha.search.SearchedWordsListAdapter(onSearchClick)
    }
}
