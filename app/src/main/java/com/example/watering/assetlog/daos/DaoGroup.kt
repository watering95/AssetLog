package com.example.watering.assetlog.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.watering.assetlog.entities.Group

@Dao
interface DaoGroup {
    @Query("SELECT * FROM tbl_Group")
    fun getAll(): LiveData<List<Group>>

    @Query("SELECT * FROM tbl_Group WHERE _id = :id LIMIT 1")
    fun get(id: Int?): LiveData<Group>

    @Query("SELECT * FROM tbl_Group WHERE name = :name")
    fun getByName(name: String?): LiveData<Group>

    @Insert
    fun insert(group: Group)

    @Update
    fun update(group: Group)

    @Delete
    fun delete(group: Group)
}