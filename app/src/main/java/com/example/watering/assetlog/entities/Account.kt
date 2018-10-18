package com.example.watering.assetlog.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Account {
    @PrimaryKey
    var id = -1
    @ColumnInfo(name = "id_group")
    var group = -1
    @ColumnInfo(name = "institute")
    var institute: String? = null
    @ColumnInfo(name = "number")
    var number: String? = null
    @ColumnInfo(name = "description")
    var description: String? = null
}