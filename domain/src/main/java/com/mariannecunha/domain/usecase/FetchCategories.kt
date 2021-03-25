package com.mariannecunha.domain.usecase

import com.mariannecunha.domain.repository.CategoryRepository

class FetchCategories(private val categories: CategoryRepository) {

    suspend operator fun invoke(): MutableList<String>? {
        return categories.fetchCategories()
    }
}
