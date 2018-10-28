package com.example.watering.assetlog.fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.watering.assetlog.R
import com.example.watering.assetlog.entities.Card

class RecyclerViewAdapterManagementCard(val lists:List<Card>): RecyclerView.Adapter<RecyclerViewAdapterManagementCard.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val cardView = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_management_card, parent, false)

        return ViewHolder(cardView)

    }

    override fun getItemCount(): Int {
        return lists.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = lists[position].name
        holder.company.text = lists[position].company
        holder.number.text = lists[position].number
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var name: TextView = view.findViewById(R.id.text_name_card_management_card)
        var company: TextView = view.findViewById(R.id.text_company_card_management_card)
        var number: TextView = view.findViewById(R.id.text_number_card_management_card)
    }
}