package com.example.watering.assetlog.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.example.watering.assetlog.entities.Group

@Dao
interface DaoGroup {
    @Query("SELECT * from tbl_Group")
    fun getAll(): LiveData<List<Group>>
}