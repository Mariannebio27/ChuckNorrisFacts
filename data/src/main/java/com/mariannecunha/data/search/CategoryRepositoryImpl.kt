package com.mariannecunha.data.search

import com.github.kittinunf.result.coroutines.SuspendableResult
import com.mariannecunha.data.search.remote.CategoryService
import com.mariannecunha.domain.repository.CategoryRepository
import java.lang.Exception

class CategoryRepositoryImpl(private val service: CategoryService) : CategoryRepository {

    override suspend fun fetchCategories(): MutableList<String>? {

        val result: SuspendableResult<MutableList<String>, Exception> =
            SuspendableResult.of {
                service.fetchCategories()
            }

        return result.component1()
    }
}
