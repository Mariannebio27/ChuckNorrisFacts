package com.mariannecunha.factlist.di

import com.mariannecunha.domain.usecase.FetchFacts
import com.mariannecunha.domain.usecase.GetWords
import com.mariannecunha.factlist.FactListAdapter
import com.mariannecunha.factlist.FactListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val factListModule = module {

    viewModel {
        FactListViewModel(
            get<FetchFacts>(),
            get<GetWords>()
        )
    }

    factory {
        FactListAdapter()
    }
}
