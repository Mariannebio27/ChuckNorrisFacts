package com.mariannecunha.domain.repository

interface CategoryRepository {
    suspend fun fetchCategories(): MutableList<String>?
}
