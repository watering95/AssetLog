package com.example.watering.assetlog.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_Info_IO_Foreign")
class IOForeign {
    @PrimaryKey
    var id = -1
    @ColumnInfo(name = "date")
    var date: String? = null
    @ColumnInfo(name = "id_account")
    var account = -1
    @ColumnInfo(name = "input")
    var input = 0.0
    @ColumnInfo(name = "output")
    var output = 0.0
    @ColumnInfo(name = "input_krw")
    var input_krw = 0
    @ColumnInfo(name = "output_krw")
    var output_krw = 0
    @ColumnInfo(name = "id_currency")
    var currency = 0
    @ColumnInfo(name = "evaluation")
    var evaluation_krw = 0
}