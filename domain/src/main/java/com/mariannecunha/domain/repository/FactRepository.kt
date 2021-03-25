package com.mariannecunha.domain.repository

import com.github.kittinunf.result.coroutines.SuspendableResult
import com.mariannecunha.domain.model.GlobalFacts
import java.lang.Exception

interface FactRepository {
    suspend fun fetchFacts(query: String?): SuspendableResult<GlobalFacts, Exception>
}
