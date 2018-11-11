package com.example.watering.assetlog.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.watering.assetlog.entities.CategorySub

@Dao
interface DaoCategorySub {
    @Query("SELECT * from tbl_category_sub")
    fun getAll(): LiveData<List<CategorySub>>

    @Insert
    fun insert(categorySub: CategorySub)

    @Update
    fun update(categorySub: CategorySub)

    @Delete
    fun delete(categorySub: CategorySub)
}