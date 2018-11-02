package com.example.watering.assetlog.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.watering.assetlog.entities.CategoryMain

@Dao
interface DaoCategoryMain {
    @Query("SELECT * from tbl_category_main")
    fun getAll(): LiveData<List<CategoryMain>>

    @Insert
    fun insert(categoryMain: CategoryMain)
}