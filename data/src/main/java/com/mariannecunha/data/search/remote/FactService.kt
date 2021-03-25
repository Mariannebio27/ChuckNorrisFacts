package com.mariannecunha.data.search.remote

import com.mariannecunha.domain.model.GlobalFacts
import retrofit2.http.GET
import retrofit2.http.Query

interface FactService {
    @GET("search")
    suspend fun fetchFacts(@Query("query") query: String?): GlobalFacts
}
