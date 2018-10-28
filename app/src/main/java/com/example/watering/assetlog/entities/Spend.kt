package com.example.watering.assetlog.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_spend")
class Spend {
    @PrimaryKey
    var id = -1
    @ColumnInfo(name = "code")
    var code: String? = null
    @ColumnInfo(name = "details")
    var details: String? = null
    @ColumnInfo(name = "category")
    var category = -1
    @ColumnInfo(name = "date")
    var date: String? = null
    @ColumnInfo(name = "amount")
    var amount = 0
}