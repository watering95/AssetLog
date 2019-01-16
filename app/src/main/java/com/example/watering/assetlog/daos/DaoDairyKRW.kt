package com.example.watering.assetlog.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.watering.assetlog.entities.DairyKRW

@Dao
interface DaoDairyKRW {
    @Query("SELECT * from tbl_Info_Dairy")
    fun getAll(): LiveData<List<DairyKRW>>

    @Query("SELECT * FROM tbl_Info_Dairy WHERE id_account = :id_account AND date = :date")
    fun get(id_account:Int?, date:String?): LiveData<DairyKRW>

    @Query("SELECT * FROM tbl_Info_Dairy WHERE id_account = :id_account AND date <= :date ORDER BY date DESC LIMIT 1")
    fun getLast(id_account:Int?, date: String?): LiveData<DairyKRW>

    @Insert
    fun insert(dairy: DairyKRW)

    @Update
    fun update(dairy: DairyKRW)

    @Delete
    fun delete(dairy: DairyKRW)
}