package com.example.watering.assetlog.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.example.watering.assetlog.entities.DairyKRW

@Dao
interface DaoDairyKRW {
    @Query("SELECT * from tbl_info_dairy")
    fun getAll(): LiveData<List<DairyKRW>>
}