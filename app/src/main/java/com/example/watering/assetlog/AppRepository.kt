package com.example.watering.assetlog

import android.app.Application
import com.example.watering.assetlog.daos.*

class AppRepository {
    var daoAccount: DaoAccount
    var daoCard: DaoCard
    var daoCategoryMain: DaoCategoryMain
    var daoCategorySub: DaoCategorySub
    var daoDairyForeign: DaoDairyForeign
    var daoDairyKRW: DaoDairyKRW
    var daoDairyTotal: DaoDairyTotal
    var daoGroup: DaoGroup
    var daoIncome: DaoIncome
    var daoIOForeign: DaoIOForeign
    var daoIOKRW: DaoIOKRW
    var daoSpend: DaoSpend
    var daoSpendCard: DaoSpendCard
    var daoSpendCash: DaoSpendCash


    constructor(application: Application) {
        var db = AppDatabase.getDatabase(application)
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
    }
}