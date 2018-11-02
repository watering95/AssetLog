package com.example.watering.assetlog.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_Info_Dairy_Total")
class DairyTotal {
    @ColumnInfo(name = "_id")
    @PrimaryKey(autoGenerate = true)
    var id: Int? = -1
    @ColumnInfo(name = "date")
    var date: String? = null
    @ColumnInfo(name = "id_account")
    var account: Int? = -1
    @ColumnInfo(name = "principal")
    var principal_krw: Int? = 0
    @ColumnInfo(name = "evaluation")
    var evaluation_krw: Int? = 0
    @ColumnInfo(name = "rate")
    var rate: Double? = 0.0
}