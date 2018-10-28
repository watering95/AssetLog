package com.example.watering.assetlog.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_Info_Dairy")
class DairyKRW {
    @PrimaryKey
    var id = -1
    @ColumnInfo(name = "date")
    var date: String? = null
    @ColumnInfo(name = "id_account")
    var account = -1
    @ColumnInfo(name = "principal")
    var principal_krw = 0
    @ColumnInfo(name = "rate")
    var rate = 0.0
}