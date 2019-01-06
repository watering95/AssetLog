package com.example.watering.assetlog.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.watering.assetlog.R
import com.example.watering.assetlog.entities.DairyTotal
import java.util.*

class RecyclerViewAdapterAccounts(val lists:List<DairyTotal>, private val clickListener: (Int) -> Unit): RecyclerView.Adapter<RecyclerViewAdapterAccounts.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val cardView = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_accounts, parent, false)

        return ViewHolder(cardView)

    }

    override fun getItemCount(): Int {
        return lists.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(lists[position], position, clickListener)
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        private var date: TextView = view.findViewById(R.id.text_date_card_accounts)
        private var principal: TextView = view.findViewById(R.id.text_principal_card_accounts)
        private var evaluation: TextView = view.findViewById(R.id.text_evaluation_card_accounts)
        private var rate: TextView = view.findViewById(R.id.text_rate_card_accounts)

        fun bind(dairyTotal: DairyTotal, position: Int, clickListener: (Int) -> Unit) {
            date.text = dairyTotal.date
            principal.text = String.format(Locale.getDefault(),"%d",dairyTotal.principal_krw)
            evaluation.text = String.format(Locale.getDefault(),"%d",dairyTotal.evaluation_krw)
            rate.text = String.format(Locale.getDefault(),"%.2f",dairyTotal.rate)
            view.setOnClickListener { clickListener(position) }
        }
    }
}