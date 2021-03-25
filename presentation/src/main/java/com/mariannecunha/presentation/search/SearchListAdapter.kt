package com.mariannecunha.presentation.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mariannecunha.core.livedata.SingleEventLiveData
import com.mariannecunha.presentation.R

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
        val itemView: View =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.tag_cloud_item, parent, false)

        return SearchListViewHolder(
            itemView
        )
    }

    override fun getItemCount(): Int {
        return categories.count()
    }

    override fun onBindViewHolder(holder: SearchListViewHolder, position: Int) {
        holder.itemBind(categories[position], _clickSearchLiveData)
    }

    class SearchListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val categoryTextView = itemView.findViewById<TextView>(R.id.first_category_text_view)

        fun itemBind(category: String, _clickSearchLiveData: SingleEventLiveData<String>) {

            categoryTextView.text = category.toUpperCase()

            setUpClickCategory(category, _clickSearchLiveData)
        }

        private fun setUpClickCategory(category: String, _clickSearchLiveData: SingleEventLiveData<String>) {

            itemView.setOnClickListener {
                _clickSearchLiveData.postValue(category)
            }
        }
    }
}
