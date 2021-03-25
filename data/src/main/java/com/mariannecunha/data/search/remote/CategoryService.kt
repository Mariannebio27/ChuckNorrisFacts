package com.mariannecunha.data.search.remote

import retrofit2.http.GET

interface CategoryService {
    @GET("categories")
    suspend fun fetchCategories(): MutableList<String>
}
