package com.example.watering.assetlog.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.example.watering.assetlog.entities.Income

@Dao
interface DaoIncome {
    @Query("SELECT * from tbl_income")
    fun getAll(): LiveData<List<Income>>
}