package com.example.watering.assetlog.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.watering.assetlog.entities.DairyForeign

@Dao
interface DaoDairyForeign {
    @Query("SELECT * from tbl_info_dairy_foreign")
    fun getAll(): LiveData<List<DairyForeign>>

    @Query("SELECT * FROM tbl_info_dairy_foreign WHERE id_account = :id_account AND date = :date AND id_currency = :currency")
    fun get(id_account: Int?, date: String?, currency: Int?): LiveData<DairyForeign>

    @Query("SELECT * FROM tbl_info_dairy_foreign WHERE id_account = :id_account AND date = :date")
    fun getForDate(id_account: Int?, date: String?): LiveData<List<DairyForeign>>

    @Insert
    fun insert(dairy: DairyForeign)

    @Update
    fun update(dairy: DairyForeign)

    @Delete
    fun delete(dairy: DairyForeign)
}