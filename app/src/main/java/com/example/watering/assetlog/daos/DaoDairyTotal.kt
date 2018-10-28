package com.example.watering.assetlog.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.example.watering.assetlog.entities.DairyTotal

@Dao
interface DaoDairyTotal {
    @Query("SELECT * from tbl_info_dairy_total")
    fun getAll(): LiveData<List<DairyTotal>>
}