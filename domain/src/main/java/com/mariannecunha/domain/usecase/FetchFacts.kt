package com.mariannecunha.domain.usecase

import com.github.kittinunf.result.coroutines.SuspendableResult
import com.mariannecunha.domain.model.GlobalFacts
import com.mariannecunha.domain.repository.FactRepository
import java.lang.Exception

class FetchFacts(private val facts: FactRepository) {

    suspend operator fun invoke(query: String?): SuspendableResult<GlobalFacts, Exception> {
        return facts.fetchFacts(query)
    }
}
