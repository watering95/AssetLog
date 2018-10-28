package com.example.watering.assetlog.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_income")
class Income {
    @PrimaryKey
    var id = -1
    @ColumnInfo(name = "date")
    var date: String? = null
    @ColumnInfo(name = "id_sub")
    var category = -1
    @ColumnInfo(name = "amount")
    var amount = 0
    @ColumnInfo(name = "details")
    var details: String? = null
    @ColumnInfo(name = "id_account")
    var account = -1
}