package com.mariannecunha.data.search

import com.mariannecunha.data.search.local.SearchHistoryCache
import com.mariannecunha.domain.repository.HistoryRepository

class HistoryRepositoryImpl(
    private val searchHistoricCache: SearchHistoryCache
) : HistoryRepository {

    override fun setSearch(search: String?) {
        val historic = getHistoric()
        val indexOfContainedString = historic.indexOf(search?.trim())
        if (indexOfContainedString != DOES_NOT_CONTAIN) historic.removeAt(indexOfContainedString)
        if (search != null) {
            historic.add(FIRST_POSITION, search)
        }

        val historicAsString = historic.joinToString()
        searchHistoricCache.setHistoricAsString(historicAsString = historicAsString)
    }

    // TODO: Refactor
    override fun getHistoric(): MutableList<String> {
        val historic = mutableListOf<String>()
        val historicAsString = searchHistoricCache.getHistoricAsString()
        val historicAsList = historicAsString?.split(DELIMITERS)

        historicAsList?.forEach { search ->
            historic.add(search.trim())
        }
        return historic
    }

    internal companion object {
        const val DOES_NOT_CONTAIN = -1
        const val FIRST_POSITION = 0
        const val DELIMITERS = ","
    }
}
