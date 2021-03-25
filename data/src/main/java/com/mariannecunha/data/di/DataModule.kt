package com.mariannecunha.data.di

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.mariannecunha.data.search.CategoryRepositoryImpl
import com.mariannecunha.data.search.FactRepositoryImpl
import com.mariannecunha.data.search.HistoryRepositoryImpl
import com.mariannecunha.data.search.local.SearchHistoryCache
import com.mariannecunha.data.search.remote.CategoryService
import com.mariannecunha.data.search.remote.FactService
import com.mariannecunha.domain.repository.CategoryRepository
import com.mariannecunha.domain.repository.FactRepository
import com.mariannecunha.domain.repository.HistoryRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val dataModule = module {
    factory {
        FactRepositoryImpl(
            get<FactService>()
        ) as FactRepository
    }

    factory {
        CategoryRepositoryImpl(
            get<CategoryService>()
        ) as CategoryRepository
    }

    factory {
        HistoryRepositoryImpl(
            get<SearchHistoryCache>()
        ) as HistoryRepository
    }

    factory {
        createFactService(
            get<Retrofit>()
        )
    }

    factory {
        SearchHistoryCache(
            get<SharedPreferences>()
        )
    }

    factory {
        createCategoryService(
            get<Retrofit>()
        )
    }

    single {
        createRetrofit(
            get<OkHttpClient>()
        )
    }

    factory {
        createOkHttpClient()
    }

    single {
        createSharedPreferences(androidContext())
    }
}

private fun createFactService(retrofit: Retrofit): FactService {
    return retrofit.create(FactService::class.java)
}

private fun createCategoryService(retrofit: Retrofit): CategoryService {
    return retrofit.create(CategoryService::class.java)
}

private fun createRetrofit(okHttpClient: OkHttpClient): Retrofit {

    return Retrofit.Builder()
        .baseUrl("https://api.chucknorris.io/jokes/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

private fun createOkHttpClient(): OkHttpClient {
    val timeoutInSeconds = 10L
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC

    return OkHttpClient.Builder()
        .connectTimeout(timeoutInSeconds, TimeUnit.SECONDS)
        .readTimeout(timeoutInSeconds, TimeUnit.SECONDS)
        .addInterceptor(httpLoggingInterceptor).build()
}

private fun createSharedPreferences(context: Context) =
    PreferenceManager.getDefaultSharedPreferences(context)
