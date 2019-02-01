package com.example.watering.assetlog.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.watering.assetlog.entities.Spend

@Dao
interface DaoSpend {
    @Query("SELECT * from tbl_spend")
    fun getAll(): LiveData<List<Spend>>

    @Query("SELECT * from tbl_spend WHERE date_use = :date")
    fun get(date: String?): LiveData<List<Spend>>

    @Query("SELECT spend_code from tbl_spend WHERE date_use = :date ORDER BY spend_code DESC LIMIT 1")
    fun getLastCode(date: String?): LiveData<String>

    @Query("SELECT total(A.amount) FROM tbl_spend AS A LEFT JOIN tbl_spend_cash AS B ON A.spend_code = B.spend_code WHERE B.id_account = :id_account AND A.date_use = :date")
    fun sumOfSpendsCash(id_account: Int?, date: String?): LiveData<Int>

    @Query("SELECT total(A.amount) FROM tbl_spend AS A LEFT JOIN tbl_spend_card AS B ON A.spend_code = B.spend_code JOIN tbl_card AS C ON B.id_card = C._id WHERE C.id_account = :id_account AND A.date_use = :date")
    fun sumOfSpendsCard(id_account: Int?, date: String?): LiveData<Int>

    @Insert
    fun insert(spend: Spend)

    @Update
    fun update(spend: Spend)

    @Delete
    fun delete(spend: Spend)
}