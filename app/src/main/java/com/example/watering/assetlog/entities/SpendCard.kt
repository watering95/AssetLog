package com.example.watering.assetlog.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_spend_card", indices = [] )
class SpendCard {
    @ColumnInfo(name = "_id")
    @PrimaryKey(autoGenerate = true)
    var id: Int? = -1
    @ColumnInfo(name = "spend_code")
    var code: String? = null
    @ColumnInfo(name = "id_card")
    var card: Int? = -1
}