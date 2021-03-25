package com.mariannecunha.domain.usecase

import com.mariannecunha.domain.repository.HistoryRepository

class GetWords(private val historyRepository: HistoryRepository) {

    operator fun invoke(): MutableList<String> {
        val list = historyRepository.getHistoric()
        val listSize = list.size

        return if (listSize >= 6) {
            list.subList(0, 6)
        } else {
            list
        }
    }
}
