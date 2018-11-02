package com.example.watering.assetlog.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_spend", indices = [] )
class Spend {
    @ColumnInfo(name = "_id")
    @PrimaryKey(autoGenerate = true)
    var id: Int? = -1
    @ColumnInfo(name = "spend_code")
    var code: String? = null
    @ColumnInfo(name = "details")
    var details: String? = null
    @ColumnInfo(name = "id_sub")
    var category: Int? = -1
    @ColumnInfo(name = "date_use")
    var date: String? = null
    @ColumnInfo(name = "amount")
    var amount: Int? = 0
}