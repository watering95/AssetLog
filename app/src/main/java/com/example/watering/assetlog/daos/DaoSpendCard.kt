package com.example.watering.assetlog.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.example.watering.assetlog.entities.SpendCard

@Dao
interface DaoSpendCard {
    @Query("SELECT * from tbl_spend_card")
    fun getAll(): LiveData<List<SpendCard>>

    @Query("SELECT * from tbl_spend_card WHERE spend_code = :code LIMIT 1")
    fun get(code: String?): LiveData<SpendCard>
}