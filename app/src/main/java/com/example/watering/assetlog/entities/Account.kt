package com.example.watering.assetlog.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_Account", indices = [] )
class Account {
    @ColumnInfo(name = "_id")
    @PrimaryKey(autoGenerate = true)
    var id:Int? = -1
    @ColumnInfo(name = "id_group")
    var group:Int? = -1
    @ColumnInfo(name = "institute")
    var institute: String? = null
    @ColumnInfo(name = "number")
    var number: String? = null
    @ColumnInfo(name = "description")
    var description: String? = null
}