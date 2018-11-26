package com.example.watering.assetlog.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.example.watering.assetlog.entities.SpendCash

@Dao
interface DaoSpendCash {
    @Query("SELECT * from tbl_spend_cash")
    fun getAll(): LiveData<List<SpendCash>>

    @Query("SELECT * from tbl_spend_cash WHERE spend_code = :code LIMIT 1")
    fun get(code: String?): LiveData<SpendCash>
}