package com.example.clientserverpaa.utilities

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.clientserverpaa.R

data class HistoryItem(val query: String, val timestamp: Long)

class HistoryAdapter(
    private val historyList: List<HistoryItem>,
    private val onItemClick: (String) -> Unit
) : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    inner class HistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val queryTextView: TextView = itemView.findViewById(R.id.queryTextView)

        fun bind(item: HistoryItem) {
            queryTextView.text = item.query
            itemView.setOnClickListener {
                onItemClick(item.query)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_history, parent, false)
        return HistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(historyList[position])
    }

    override fun getItemCount(): Int = historyList.size
}