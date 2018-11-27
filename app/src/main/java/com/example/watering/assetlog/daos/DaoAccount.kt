package com.example.watering.assetlog.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.watering.assetlog.entities.Account

@Dao
interface DaoAccount {
    @Query("SELECT * from tbl_Account")
    fun getAll(): LiveData<List<Account>>

    @Query("SELECT * FROM tbl_Account WHERE _id = :id LIMIT 1")
    fun get(id: Int?): LiveData<Account>

    @Query("SELECT * FROM tbl_Account WHERE id_group = :id")
    fun getByGroup(id: Int?): LiveData<List<Account>>

    @Query("SELECT * FROM tbl_Account INNER JOIN tbl_spend_cash on tbl_spend_cash.id_account = tbl_Account._id WHERE tbl_spend_cash.spend_code = :code LIMIT 1")
    fun getByCode(code: String?): LiveData<Account>

    @Insert
    fun insert(account: Account)

    @Update
    fun update(account: Account)

    @Delete
    fun delete(account: Account)
}