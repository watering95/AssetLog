package com.example.watering.assetlog.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.example.watering.assetlog.entities.IOKRW

@Dao
interface DaoIOKRW {
    @Query("SELECT * from tbl_Info_IO")
    fun getAll(): LiveData<List<IOKRW>>
}