package com.example.watering.assetlog.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.watering.assetlog.entities.DairyTotal

@Dao
interface DaoDairyTotal {
    @Query("SELECT * from tbl_Info_Dairy_Total")
    fun getAll(): LiveData<List<DairyTotal>>

    @Query("SELECT * FROM tbl_Info_Dairy_Total WHERE id_account = :id_account AND date = :date")
    fun get(id_account:Int?, date:String?): LiveData<DairyTotal>

    @Query("SELECT * FROM tbl_Info_Dairy_Total WHERE id_account = :id_account ORDER BY date DESC")
    fun getLogs(id_account: Int?): LiveData<List<DairyTotal>>

    @Insert
    fun insert(dairy: DairyTotal)

    @Update
    fun update(dairy: DairyTotal)

    @Delete
    fun delete(dairy: DairyTotal)
}