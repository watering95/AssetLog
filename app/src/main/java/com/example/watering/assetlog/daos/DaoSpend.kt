package com.example.watering.assetlog.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.example.watering.assetlog.entities.Spend

@Dao
interface DaoSpend {
    @Query("SELECT * from tbl_spend")
    fun getAll(): LiveData<List<Spend>>
}