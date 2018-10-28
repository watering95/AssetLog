package com.example.watering.assetlog.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_Group")
class Group {
    @PrimaryKey
    var id = -1
    @ColumnInfo(name = "name")
    var name: String? = null
}