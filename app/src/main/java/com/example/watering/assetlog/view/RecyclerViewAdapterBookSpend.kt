package com.example.watering.assetlog.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.watering.assetlog.R
import com.example.watering.assetlog.entities.Spend

class RecyclerViewAdapterBookSpend(val lists:List<Spend>, private val clickListener: (Int) -> Unit): RecyclerView.Adapter<RecyclerViewAdapterBookSpend.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val cardView = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_book_spend, parent, false)

        return ViewHolder(cardView)

    }

    override fun getItemCount(): Int {
        return lists.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(lists[position], position, clickListener)
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(spend: Spend, position: Int, clickListener: (Int) -> Unit) {
            view.setOnClickListener { clickListener(position) }
        }
    }
}