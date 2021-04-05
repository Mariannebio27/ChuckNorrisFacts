package com.mariannecunha.domain.di

import com.mariannecunha.domain.repository.CategoryRepository
import com.mariannecunha.domain.repository.FactRepository
import com.mariannecunha.domain.repository.HistoryRepository
import com.mariannecunha.domain.usecase.FetchCategories
import com.mariannecunha.domain.usecase.FetchFacts
import com.mariannecunha.domain.usecase.GetWords
import com.mariannecunha.domain.usecase.SaveWords
import org.koin.dsl.module

val domainModule = module {

    factory {
        FetchFacts(
            get<FactRepository>()
        )
    }

    factory {
        FetchCategories(
            get<CategoryRepository>()
        )
    }

    factory {
        SaveWords(
            get<HistoryRepository>()
        )
    }

    factory {
        GetWords(
            get<HistoryRepository>()
        )
    }
}
