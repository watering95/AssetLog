package com.example.watering.assetlog.entities

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_Group", indices = [] )
data class Group (@ColumnInfo(name = "name") var name: String?){
    @ColumnInfo(name = "_id")
    @PrimaryKey(autoGenerate = true)
    var id:Int? = -1
}