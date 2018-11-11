package com.example.watering.assetlog.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.watering.assetlog.entities.Group

@Dao
interface DaoGroup {
    @Query("SELECT * FROM tbl_Group")
    fun getAll(): LiveData<List<Group>>

    @Query("SELECT * FROM tbl_Group WHERE _id = :groupId LIMIT 1")
    fun get(groupId: Int?): LiveData<Group>

    @Insert
    fun insert(group: Group)

    @Update
    fun update(group: Group)

    @Delete
    fun delete(group: Group)
}