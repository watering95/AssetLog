package com.example.watering.assetlog.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_Info_IO")
class IOKRW {
    @ColumnInfo(name = "_id")
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
    @ColumnInfo(name = "date")
    var date: String? = null
    @ColumnInfo(name = "id_account")
    var account: Int? = null
    @ColumnInfo(name = "input")
    var input: Int? = 0
    @ColumnInfo(name = "output")
    var output: Int? = 0
    @ColumnInfo(name = "income")
    var income: Int? = 0
    @ColumnInfo(name = "spend_cash")
    var spend_cash: Int? = 0
    @ColumnInfo(name = "spend_card")
    var spend_card: Int? = 0
    @ColumnInfo(name = "evaluation")
    var evaluation_krw: Int? = 0
}