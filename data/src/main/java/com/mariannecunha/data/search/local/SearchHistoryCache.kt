package com.mariannecunha.data.search.local

import android.content.SharedPreferences

class SearchHistoryCache(private val sharedPreferences: SharedPreferences) {

    fun setHistoricAsString(historicAsString: String?) {
        val editor = sharedPreferences.edit()
        editor.putString(SEARCH_HISTORIC_KEY, historicAsString)
        editor.apply()
    }

    fun getHistoricAsString(): String? {

        return sharedPreferences.getString(SEARCH_HISTORIC_KEY, EMPTY_STRING)
    }

    private companion object {
        const val SEARCH_HISTORIC_KEY = "SEARCH_HISTORIC_KEY"
        const val EMPTY_STRING = ""
    }
}
