package com.mariannecunha.factlist

import android.content.Intent
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mariannecunha.domain.model.Fact
import com.mariannecunha.factlist.databinding.FactListItemBinding
import java.util.Locale

class FactListAdapter() : RecyclerView.Adapter<FactListAdapter.FactListViewHolder>() {

    private val facts = mutableListOf<Fact>()

    fun updateFacts(facts: List<Fact>?) {

        this.facts.addAll(facts ?: listOf())
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FactListViewHolder {
        val binding = FactListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return FactListViewHolder(
            binding
        )
    }

    override fun getItemCount(): Int {
        return facts.count()
    }

    override fun onBindViewHolder(holder: FactListViewHolder, position: Int) {
        holder.itemBind(facts[position])
    }

    class FactListViewHolder(private val binding: FactListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun itemBind(facts: Fact) = with(binding) {

            valueTextView.text = facts.value
            firstCategoryTextView.text = getCategories(facts)

            setUpShare(facts)
            handleTextSize(valueTextView)
        }

        private fun getCategories(facts: Fact): String {
            return if (facts.categories.isEmpty()) {
                "UNCATEGORIZED"
            } else {
                facts.categories.toString().removeSurrounding("[", "]").toUpperCase(Locale.getDefault())
            }
        }

        private fun setUpShare(facts: Fact) {
            binding.shareImageView.setOnClickListener {
                val i = Intent(Intent.ACTION_SEND)
                i.type = "text/plain"
                i.putExtra(Intent.EXTRA_SUBJECT, "Sharing URL")
                i.putExtra(Intent.EXTRA_TEXT, facts.url)
                itemView.context.startActivity(Intent.createChooser(i, "Share URL"))
            }
        }

        private fun handleTextSize(text: TextView) {
            val textSize = text.text.length
            if (textSize > 80) {
                text.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f)
            } else {
                text.setTextSize(TypedValue.COMPLEX_UNIT_SP, 21f)
            }
        }
    }
}
