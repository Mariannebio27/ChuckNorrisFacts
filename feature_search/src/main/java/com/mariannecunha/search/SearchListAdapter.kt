package com.mariannecunha.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mariannecunha.core.livedata.SingleEventLiveData
import com.mariannecunha.search.databinding.TagCloudItemBinding

class SearchListAdapter(private val _clickSearchLiveData: SingleEventLiveData<String>) : RecyclerView.Adapter<SearchListAdapter.SearchListViewHolder>() {

    private val categories = mutableListOf<String>()

    fun updateCategories(categories: MutableList<String>) {

        if (this.categories.isNotEmpty()) {
            this.categories.clear()
        }

//        TODO(change shuffled to usecase)
        val updatedCategories = categories.shuffled().subList(0, 8)

        this.categories.addAll(updatedCategories ?: listOf())
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchListViewHolder {
        val binding = TagCloudItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return SearchListViewHolder(
            binding
        )
    }

    override fun getItemCount(): Int {
        return categories.count()
    }

    override fun onBindViewHolder(holder: SearchListViewHolder, position: Int) {
        holder.itemBind(categories[position], _clickSearchLiveData)
    }

    class SearchListViewHolder(private val binding: TagCloudItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun itemBind(category: String, _clickSearchLiveData: SingleEventLiveData<String>) {

            binding.firstCategoryTextView.text = category.toUpperCase()

            setUpClickCategory(category, _clickSearchLiveData)
        }

        private fun setUpClickCategory(category: String, _clickSearchLiveData: SingleEventLiveData<String>) {

            itemView.setOnClickListener {
                _clickSearchLiveData.postValue(category)
            }
        }
    }
}
