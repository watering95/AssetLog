package com.example.watering.assetlog

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.watering.assetlog.daos.*
import com.example.watering.assetlog.entities.*

@Database(entities = [Account::class, Card::class, CategoryMain::class, CategorySub::class, DairyForeign::class, DairyKRW::class, DairyTotal::class, Group::class, Income::class, IOForeign::class, IOKRW::class, Spend::class, SpendCard::class, SpendCash::class], version = 7)
abstract class AppDatabase: RoomDatabase() {

    abstract fun daoAccount(): DaoAccount
    abstract fun daoCard(): DaoCard
    abstract fun daoCategoryMain(): DaoCategoryMain
    abstract fun daoCategorySub(): DaoCategorySub
    abstract fun daoDairyForeign(): DaoDairyForeign
    abstract fun daoDairyKRW(): DaoDairyKRW
    abstract fun daoDairyTotal(): DaoDairyTotal
    abstract fun daoGroup(): DaoGroup
    abstract fun daoIncome(): DaoIncome
    abstract fun daoIOForeign(): DaoIOForeign
    abstract fun daoIOKRW(): DaoIOKRW
    abstract fun daoSpend(): DaoSpend
    abstract fun daoSpendCard(): DaoSpendCard
    abstract fun daoSpendCash(): DaoSpendCash

    companion object {
        private lateinit var INSTANCE: AppDatabase

        fun getDatabase(context: Context): AppDatabase {
            if(INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java,
                        "AssetLog.db").build()
                }
            }
            return INSTANCE
        }
    }
}