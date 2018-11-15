package com.example.watering.assetlog.model

import java.util.*

class AppModel {
    fun getToday(): String {
        val today = Calendar.getInstance()
        return String.format(Locale.getDefault(), "%04d-%02d-%02d", today.get(Calendar.YEAR),today.get(Calendar.MONTH)+1,today.get(Calendar.DATE))
    }
}