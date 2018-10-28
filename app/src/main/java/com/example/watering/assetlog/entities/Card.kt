package com.example.watering.assetlog.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_card")
class Card {
    @PrimaryKey
    var id = -1
    @ColumnInfo(name = "number")
    var number: String? = null
    @ColumnInfo(name = "company")
    var company: String? = null
    @ColumnInfo(name = "name")
    var name: String? = null
    @ColumnInfo(name = "date_draw")
    var drawDate = -1
    @ColumnInfo(name = "id_account")
    var account = -1
}