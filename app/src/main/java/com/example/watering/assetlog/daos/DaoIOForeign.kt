package com.example.watering.assetlog.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.example.watering.assetlog.entities.IOForeign

@Dao
interface DaoIOForeign {
    @Query("SELECT * from tbl_Info_IO_Foreign")
    fun getAll(): LiveData<List<IOForeign>>
}