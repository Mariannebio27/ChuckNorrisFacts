package com.mariannecunha.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SearchedWordsListAdapter(private val onSearchClick: (String) -> Unit) :
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
        holder.itemBind(words[position], onSearchClick)
    }

    class SearchedWordsListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val searchedWordTextView =
            itemView.findViewById<TextView>(R.id.searched_word_text_view)

        fun itemBind(word: String, onSearchClick: (String) -> Unit) {

            searchedWordTextView.text = word

            setUpClickWord(word, onSearchClick)
        }

        private fun setUpClickWord(
            word: String,
            onSearchClick: (String) -> Unit
        ) {

            itemView.setOnClickListener {
                onSearchClick.invoke(word)
            }
        }
    }
}
