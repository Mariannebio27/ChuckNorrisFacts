package com.mariannecunha.data.search

import com.github.kittinunf.result.coroutines.SuspendableResult
import com.mariannecunha.data.search.remote.FactService
import com.mariannecunha.domain.model.GlobalFacts
import com.mariannecunha.domain.repository.FactRepository
import java.lang.Exception

class FactRepositoryImpl(private val service: FactService) : FactRepository {

    override suspend fun fetchFacts(query: String?): SuspendableResult<GlobalFacts, Exception> {

        return SuspendableResult.of {
            service.fetchFacts(query = query)
        }
    }
}
