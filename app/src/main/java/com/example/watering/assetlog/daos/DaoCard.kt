package com.example.watering.assetlog.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.watering.assetlog.entities.Card

@Dao
interface DaoCard {
    @Query("SELECT * from tbl_card")
    fun getAll(): LiveData<List<Card>>

    @Query("SELECT * FROM tbl_card WHERE _id = :id LIMIT 1")
    fun get(id: Int?): LiveData<Card>

    @Query("SELECT * FROM tbl_card INNER JOIN tbl_spend_card on tbl_spend_card.id_card = tbl_card._id WHERE tbl_spend_card.spend_code = :code LIMIT 1")
    fun getByCode(code: String?): LiveData<Card>

    @Insert
    fun insert(card: Card)

    @Update
    fun update(card: Card)

    @Delete
    fun delete(card: Card)
}