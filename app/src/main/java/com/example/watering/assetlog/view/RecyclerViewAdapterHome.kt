package com.example.watering.assetlog.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.watering.assetlog.R
import com.example.watering.assetlog.entities.DairyTotal
import java.util.*

class RecyclerViewAdapterHome(val lists:List<DairyTotal>, private val clickListener: (Int) -> Unit): RecyclerView.Adapter<RecyclerViewAdapterHome.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val cardView = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_home, parent, false)

        return ViewHolder(cardView)
    }

    override fun getItemCount(): Int {
        return lists.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(lists[position], position, clickListener)
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        private var accountNumber: TextView = view.findViewById(R.id.text_account_num_card_home)
        private var principal: TextView = view.findViewById(R.id.text_principal_card_home)
        private var evaluation: TextView = view.findViewById(R.id.text_evaluation_card_home)
        private var rate: TextView = view.findViewById(R.id.text_rate_card_home)

        fun bind(dairyTotal: DairyTotal, position: Int, clickListener: (Int) -> Unit) {

        }
    }
}