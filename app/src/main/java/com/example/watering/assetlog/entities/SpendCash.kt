package com.example.watering.assetlog.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_spend_cash")
class SpendCash {
    @PrimaryKey
    var id = -1
    @ColumnInfo(name = "code")
    var code: String? = null
    @ColumnInfo(name = "id_account")
    var account = -1
}