package com.mariannecunha.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mariannecunha.search.databinding.RecentSearchedItemBinding

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
        val binding = RecentSearchedItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return SearchedWordsListViewHolder(
            binding
        )
    }

    override fun getItemCount(): Int {
        return words.count()
    }

    override fun onBindViewHolder(holder: SearchedWordsListViewHolder, position: Int) {
        holder.itemBind(words[position], onSearchClick)
    }

    class SearchedWordsListViewHolder(private val binding: RecentSearchedItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun itemBind(word: String, onSearchClick: (String) -> Unit) {

            binding.searchedWordTextView.text = word

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
