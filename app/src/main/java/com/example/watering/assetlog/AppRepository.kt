package com.example.watering.assetlog

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.watering.assetlog.daos.*
import com.example.watering.assetlog.entities.Group

class AppRepository(application: Application) {
    private var daoAccount: DaoAccount
    private var daoCard: DaoCard
    private var daoCategoryMain: DaoCategoryMain
    private var daoCategorySub: DaoCategorySub
    private var daoDairyForeign: DaoDairyForeign
    private var daoDairyKRW: DaoDairyKRW
    private var daoDairyTotal: DaoDairyTotal
    private var daoGroup: DaoGroup
    private var daoIncome: DaoIncome
    private var daoIOForeign: DaoIOForeign
    private var daoIOKRW: DaoIOKRW
    private var daoSpend: DaoSpend
    private var daoSpendCard: DaoSpendCard
    private var daoSpendCash: DaoSpendCash

    private var mGroups: LiveData<List<Group>>


    init {
        val db = AppDatabase.getDatabase(application)
        daoAccount = db.daoAccount()
        daoCard = db.daoCard()
        daoCategoryMain = db.daoCategoryMain()
        daoCategorySub = db.daoCategorySub()
        daoDairyForeign = db.daoDairyForeign()
        daoDairyKRW = db.daoDairyKRW()
        daoDairyTotal = db.daoDairyTotal()
        daoGroup = db.daoGroup()
        daoIncome = db.daoIncome()
        daoIOForeign = db.daoIOForeign()
        daoIOKRW = db.daoIOKRW()
        daoSpend = db.daoSpend()
        daoSpendCard = db.daoSpendCard()
        daoSpendCash = db.daoSpendCash()
        mGroups = daoGroup.getAll()
    }

    fun getAllGroup(): LiveData<List<Group>> {
        return mGroups
    }
}