package com.example.watering.assetlog.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.watering.assetlog.entities.Account

@Dao
interface DaoAccount {
    @Query("SELECT * from tbl_Account")
    fun getAll(): LiveData<List<Account>>

    @Insert
    fun insert(account: Account)

    @Update
    fun update(account: Account)

    @Delete
    fun delete(account: Account)
}