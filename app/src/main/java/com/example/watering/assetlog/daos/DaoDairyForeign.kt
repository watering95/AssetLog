package com.example.watering.assetlog.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.example.watering.assetlog.entities.DairyForeign

@Dao
interface DaoDairyForeign {
    @Query("SELECT * from tbl_info_dairy_foreign")
    fun getAll(): LiveData<List<DairyForeign>>
}