package com.example.watering.assetlog.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.example.watering.assetlog.entities.Card

@Dao
interface DaoCard {
    @Query("SELECT * from tbl_card")
    fun getAll(): LiveData<List<Card>>
}