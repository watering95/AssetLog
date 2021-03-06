package com.example.watering.assetlog.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.watering.assetlog.R

class RecyclerViewAdapterManagementDB(val lists:ArrayList<String>, private val clickListener: (Int) -> Unit): RecyclerView.Adapter<RecyclerViewAdapterManagementDB.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val cardView = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_management_db, parent, false)

        return ViewHolder(cardView)

    }

    override fun getItemCount(): Int {
        return lists.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(lists[position], position, clickListener)
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        private var title: TextView = view.findViewById(R.id.text_title_management_db)

        fun bind(list: String, position: Int, clickListener: (Int) -> Unit) {
            title.text = list
            view.setOnClickListener { clickListener(position) }
        }
    }
}