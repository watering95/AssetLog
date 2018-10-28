package com.example.watering.assetlog.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_spend_card")
class SpendCard {
    @PrimaryKey
    var id = -1
    @ColumnInfo(name = "code")
    var code: String? = null
    @ColumnInfo(name = "id_card")
    var card = -1
}