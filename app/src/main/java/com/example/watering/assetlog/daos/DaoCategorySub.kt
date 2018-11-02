package com.example.watering.assetlog.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.watering.assetlog.entities.CategorySub

@Dao
interface DaoCategorySub {
    @Query("SELECT * from tbl_category_sub")
    fun getAll(): LiveData<List<CategorySub>>

    @Insert
    fun insert(categorySub: CategorySub)
}