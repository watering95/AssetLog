package com.example.watering.assetlog.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.watering.assetlog.entities.DairyKRW

@Dao
interface DaoDairyKRW {
    @Query("SELECT * from tbl_info_dairy")
    fun getAll(): LiveData<List<DairyKRW>>

    @Query("SELECT * FROM tbl_info_dairy WHERE id_account = :id_account AND date = :date")
    fun get(id_account:Int?, date:String?): LiveData<DairyKRW>

    @Insert
    fun insert(dairy: DairyKRW)

    @Update
    fun update(dairy: DairyKRW)

    @Delete
    fun delete(dairy: DairyKRW)
}