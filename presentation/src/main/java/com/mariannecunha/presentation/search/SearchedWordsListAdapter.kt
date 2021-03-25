package com.mariannecunha.presentation.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mariannecunha.core.livedata.SingleEventLiveData
import com.mariannecunha.presentation.R

class SearchedWordsListAdapter(private val _clickSearchLiveData: SingleEventLiveData<String>) :
    RecyclerView.Adapter<SearchedWordsListAdapter.SearchedWordsListViewHolder>() {

    private val words = mutableListOf<String>()

    fun updateWords(words: MutableList<String>) {

        if (this.words.isNotEmpty()) {
            this.words.clear()
        }

        this.words.addAll(words ?: listOf())
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchedWordsListViewHolder {
        val itemView: View =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recent_searched_item, parent, false)

        return SearchedWordsListViewHolder(
            itemView
        )
    }

    override fun getItemCount(): Int {
        return words.count()
    }

    override fun onBindViewHolder(holder: SearchedWordsListViewHolder, position: Int) {
        holder.itemBind(words[position], _clickSearchLiveData)
    }

    class SearchedWordsListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val searchedWordTextView =
            itemView.findViewById<TextView>(R.id.searched_word_text_view)

        fun itemBind(word: String, _clickSearchLiveData: SingleEventLiveData<String>) {

            searchedWordTextView.text = word

            setUpClickWord(word, _clickSearchLiveData)
        }

        private fun setUpClickWord(
            word: String,
            _clickSearchLiveData: SingleEventLiveData<String>
        ) {

            itemView.setOnClickListener {
                _clickSearchLiveData.postValue(word)
            }
        }
    }
}
