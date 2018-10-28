package com.example.watering.assetlog.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.example.watering.assetlog.entities.Account

@Dao
interface DaoAccount {
    @Query("SELECT * from tbl_Account")
    fun getAll(): LiveData<List<Account>>
}