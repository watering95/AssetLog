package com.example.watering.assetlog.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class CategoryMain {
    @PrimaryKey
    var id = -1
    @ColumnInfo(name = "name")
    var name: String? = null
    @ColumnInfo(name = "kind")
    var kind: String? = null
}