package com.example.watering.assetlog.fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.watering.assetlog.R
import com.example.watering.assetlog.entities.Account

class RecyclerViewAdapterManagementAccount(val lists:List<Account>): RecyclerView.Adapter<RecyclerViewAdapterManagementAccount.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val cardView = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_management_account, parent, false)

        return ViewHolder(cardView)

    }

    override fun getItemCount(): Int {
        return lists.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.number.text = lists[position].number
        holder.institute.text = lists[position].institute
        holder.description.text = lists[position].description
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var number: TextView = view.findViewById(R.id.text_number_card_management_account)
        var institute: TextView = view.findViewById(R.id.text_institute_card_management_account)
        var description: TextView = view.findViewById(R.id.text_description_card_management_account)
    }
}