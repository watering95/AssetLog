package com.example.watering.assetlog.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_Info_IO_Foreign")
class IOForeign {
    @ColumnInfo(name = "_id")
    @PrimaryKey(autoGenerate = true)
    var id: Int? = -1
    @ColumnInfo(name = "date")
    var date: String? = null
    @ColumnInfo(name = "id_account")
    var account: Int? = -1
    @ColumnInfo(name = "input")
    var input: Double? = 0.0
    @ColumnInfo(name = "output")
    var output: Double? = 0.0
    @ColumnInfo(name = "input_krw")
    var input_krw: Int? = 0
    @ColumnInfo(name = "output_krw")
    var output_krw: Int? = 0
    @ColumnInfo(name = "id_currency")
    var currency: Int? = 0
    @ColumnInfo(name = "evaluation")
    var evaluation_krw: Double? = 0.0
}