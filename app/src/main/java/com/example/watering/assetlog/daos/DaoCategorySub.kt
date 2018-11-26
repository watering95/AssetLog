package com.example.watering.assetlog.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.watering.assetlog.entities.CategorySub

@Dao
interface DaoCategorySub {
    @Query("SELECT * from tbl_category_sub")
    fun getAll(): LiveData<List<CategorySub>>

    @Query("SELECT * from tbl_category_sub WHERE id_main = :id_main")
    fun getByMain(id_main: Int?): LiveData<List<CategorySub>>

    @Query("SELECT * from tbl_category_sub WHERE _id = :id")
    fun get(id: Int?): LiveData<CategorySub>

    @Insert
    fun insert(categorySub: CategorySub)

    @Update
    fun update(categorySub: CategorySub)

    @Delete
    fun delete(categorySub: CategorySub)
}