package com.mariannecunha.domain.usecase

import com.mariannecunha.domain.repository.HistoryRepository

class SaveWords(private val historyRepository: HistoryRepository) {

    operator fun invoke(query: String?) {
        historyRepository.setSearch(query)
    }
}
