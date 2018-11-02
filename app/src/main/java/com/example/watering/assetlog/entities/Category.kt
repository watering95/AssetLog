package com.example.watering.assetlog.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["name"], unique = true)] )
abstract class Category {
    @ColumnInfo(name = "_id")
    @PrimaryKey(autoGenerate = true)
    var id: Int? = -1
    @ColumnInfo(name = "name")
    var name: String? = null
}