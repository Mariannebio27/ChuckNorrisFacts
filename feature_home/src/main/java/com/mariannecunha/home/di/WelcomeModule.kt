package com.mariannecunha.home.di

import com.mariannecunha.domain.usecase.FetchCategories
import com.mariannecunha.domain.usecase.GetWords
import com.mariannecunha.domain.usecase.SaveWords
import com.mariannecunha.home.WelcomeViewModel
import com.mariannecunha.search.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val welcomeModule = module {

    viewModel {
        WelcomeViewModel()
    }

}