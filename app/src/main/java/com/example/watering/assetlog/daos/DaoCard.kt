package com.example.watering.assetlog.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.watering.assetlog.entities.Card

@Dao
interface DaoCard {
    @Query("SELECT * from tbl_card")
    fun getAll(): LiveData<List<Card>>

    @Insert
    fun insert(card: Card)

    @Update
    fun update(card: Card)

    @Delete
    fun delete(card: Card)
}