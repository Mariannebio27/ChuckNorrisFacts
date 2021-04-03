package com.mariannecunha.factlist

import android.content.Intent
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mariannecunha.domain.model.Fact
import java.util.Locale

class FactListAdapter() : RecyclerView.Adapter<FactListAdapter.FactListViewHolder>() {

    private val facts = mutableListOf<Fact>()

    fun updateFacts(facts: List<Fact>?) {

        this.facts.addAll(facts ?: listOf())
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FactListViewHolder {
        val itemView: View =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.fact_list_item, parent, false)

        return FactListViewHolder(
            itemView
        )
    }

    override fun getItemCount(): Int {
        return facts.count()
    }

    override fun onBindViewHolder(holder: FactListViewHolder, position: Int) {
        holder.itemBind(facts[position])
    }

    class FactListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val factCategories = itemView.findViewById<TextView>(R.id.first_category_text_view)
        private val factText = itemView.findViewById<TextView>(R.id.value_text_view)
        private val factShare = itemView.findViewById<ImageView>(R.id.share_image_view)

        fun itemBind(facts: Fact) {

            factText.text = facts.value
            factCategories.text = getCategories(facts)

            setUpShare(facts)
            handleTextSize(factText)
        }

        private fun getCategories(facts: Fact): String {
            return if (facts.categories.isEmpty()) {
                "UNCATEGORIZED"
            } else {
                facts.categories.toString().removeSurrounding("[", "]").toUpperCase(Locale.getDefault())
            }
        }

        private fun setUpShare(facts: Fact) {
            factShare.setOnClickListener {
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
