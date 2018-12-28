package com.example.watering.assetlog.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.watering.assetlog.entities.IOKRW

@Dao
interface DaoIOKRW {
    @Query("SELECT * FROM tbl_Info_IO")
    fun getAll(): LiveData<List<IOKRW>>

    @Query("SELECT * FROM tbl_Info_IO WHERE id_account = :id_account AND date = :date")
    fun get(id_account: Int?, date: String?): LiveData<IOKRW>

    @Query("SELECT * FROM tbl_Info_IO WHERE id_account = :id_account AND date <= :date ORDER BY date DESC LIMIT 1")
    fun getLast(id_account:Int?, date: String?): LiveData<IOKRW>

    @Query("SELECT total(:column) AS SUM FROM tbl_Info_IO WHERE id_account = :id_account AND date <= :date")
    fun sum(column:String?, id_account: Int?, date: String?): LiveData<Int>

    @Insert
    fun insert(io: IOKRW)

    @Update
    fun update(io: IOKRW)

    @Delete
    fun delete(io: IOKRW)
}