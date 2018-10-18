package com.example.watering.assetlog.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class IOKRW {
    @PrimaryKey
    var id = -1
    @ColumnInfo(name = "date")
    var date: String? = null
    @ColumnInfo(name = "id_account")
    var account = -1
    @ColumnInfo(name = "input")
    var input = 0
    @ColumnInfo(name = "output")
    var output = 0
    @ColumnInfo(name = "income")
    var income = 0
    @ColumnInfo(name = "spend_cash")
    var spend_cash = 0
    @ColumnInfo(name = "spend_card")
    var spend_card = 0
    @ColumnInfo(name = "evaluation")
    var evaluation_krw = 0
}