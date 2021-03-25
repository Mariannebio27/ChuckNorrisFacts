package com.mariannecunha.domain.repository

interface HistoryRepository {

    fun setSearch(search: String?)

    fun getHistoric(): MutableList<String>
}
