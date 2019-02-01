package com.example.watering.assetlog.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.watering.assetlog.entities.CategoryMain

@Dao
interface DaoCategoryMain {
    @Query("SELECT * FROM tbl_category_main")
    fun getAll(): LiveData<List<CategoryMain>>

    @Query("SELECT * FROM tbl_category_main WHERE _id = :id LIMIT 1")
    fun get(id: Int?): LiveData<CategoryMain>

    @Query("SELECT * FROM tbl_category_main WHERE kind = :kind")
    fun getByKind(kind: String?): LiveData<List<CategoryMain>>

//    @Query("SELECT * FROM tbl_category_main WHERE name = :name")
//    fun getByName(name: String?): LiveData<CategoryMain>

    @Query("SELECT * FROM tbl_category_main WHERE _id = (SELECT id_main FROM tbl_category_sub WHERE _id = :id_sub)")
    fun getBySub(id_sub: Int?): LiveData<CategoryMain>

    @Insert
    fun insert(categoryMain: CategoryMain)

    @Update
    fun update(categoryMain: CategoryMain)

    @Delete
    fun delete(categoryMain: CategoryMain)
}