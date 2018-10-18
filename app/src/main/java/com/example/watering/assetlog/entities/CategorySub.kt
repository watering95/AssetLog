package com.example.watering.assetlog.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class CategorySub {
    @PrimaryKey
    var id = -1
    @ColumnInfo(name = "name")
    var name: String? = null
    @ColumnInfo(name = "id_main")
    var categoryMain = -1
}