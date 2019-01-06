package com.example.watering.assetlog.model

import android.app.Application
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.watering.assetlog.entities.*
import kotlinx.coroutines.CoroutineScope

class AppRepository(application: Application, scope:CoroutineScope) {
    val db = AppDatabase.getDatabase(application, scope)
    private val daoGroup = db.daoGroup()
    private val daoAccount = db.daoAccount()
    private val daoCatMain = db.daoCategoryMain()
    private val daoCatSub = db.daoCategorySub()
    private val daoCard = db.daoCard()
    private val daoSpend = db.daoSpend()
    private val daoIncome = db.daoIncome()
    private val daoSpendCash = db.daoSpendCash()
    private val daoSpendCard = db.daoSpendCard()
    private val daoIOKRW = db.daoIOKRW()
    private val daoDairyKRW = db.daoDairyKRW()

    var allGroups: LiveData<List<Group>> = daoGroup.getAll()
    var allAccounts: LiveData<List<Account>> = daoAccount.getAll()
    var allCatMains: LiveData<List<CategoryMain>> = daoCatMain.getAll()
    var allCatSubs: LiveData<List<CategorySub>> = daoCatSub.getAll()
    var allCards: LiveData<List<Card>> = daoCard.getAll()

    fun getGroup(id: Int?) = daoGroup.get(id)
    fun getAccount(id: Int?) = daoAccount.get(id)
    fun getAccountByCode(code: String?) = daoAccount.getByCode(code)
    fun getAccountByNumber(number: String?) = daoAccount.getByNumber(number)
    fun getCatMain(id: Int?) = daoCatMain.get(id)
    fun getCatMainByKind(kind: String?) = daoCatMain.getByKind(kind)
    fun getCatMainBySub(id_sub: Int?) = daoCatMain.getBySub(id_sub)
    fun getCardByCode(code: String?) = daoCard.getByCode(code)
    fun getCardByNumber(number: String?) = daoCard.getByNumber(number)
    fun getIncomes(date: String?) = daoIncome.get(date)
    fun getCatSub(id: Int?) = daoCatSub.get(id)
    fun getCatSub(nameOfSub: String?, nameOfMain:String?) = daoCatSub.get(nameOfSub, nameOfMain)
    fun getCatSubsByMain(nameOfMain: String?) = daoCatSub.getByMain(nameOfMain)
    fun getCatSubsByMain(id_main: Int?) = daoCatSub.getByMain(id_main)
    fun getSpendByCode(code: String?) = daoSpend.getByCode(code)
    fun getSpends(date: String?) = daoSpend.get(date)
    fun getSpendCash(code: String?) = daoSpendCash.get(code)
    fun getSpendCard(code: String?) = daoSpendCard.get(code)
    fun getIOKRW(id_account: Int?, date: String?) = daoIOKRW.get(id_account, date)
    fun getDairyKRW(id_account: Int?, date: String?) = daoDairyKRW.get(id_account, date)

    fun getLastSpendCode(date: String?) = daoSpend.getLastCode(date)
    fun getLastIOKRW(id_account: Int?, date: String?) = daoIOKRW.getLast(id_account, date)

    fun sumOfSpendCashInDate(id_account: Int?, date: String?) = daoSpend.sumOfSpendsCash(id_account, date)
    fun sumOfSpendCardInDate(id_account: Int?, date: String?) = daoSpend.sumOfSpendsCard(id_account, date)
    fun sumOfIncomeInDate(id_account: Int?, date: String?) = daoIncome.sum(id_account, date)
    fun sumUntilDate(column: String?, id_account: Int?, date: String?) = daoIOKRW.sum(column, id_account, date)
    fun sumOfInputUntilDate(id_account: Int?, date: String?) = daoIOKRW.sumOfInput(id_account, date)
    fun sumOfOutputUntilDate(id_account: Int?, date: String?) = daoIOKRW.sumOfOutput(id_account, date)
    fun sumOfIncomeUntilDate(id_account: Int?, date: String?) = daoIOKRW.sumOfIncome(id_account, date)
    fun sumOfSpendCardUntilDate(id_account: Int?, date: String?) = daoIOKRW.sumOfSpendCard(id_account, date)
    fun sumOfSpendCashUntilDate(id_account: Int?, date: String?) = daoIOKRW.sumOfSpendCash(id_account, date)

    fun close() {
        if(db.isOpen) db.openHelper.close()
    }

    @WorkerThread
    fun <T> insert(t: T) {
        if(t is Group) daoGroup.insert(t)
        if(t is Account) daoAccount.insert(t)
        if(t is CategoryMain) daoCatMain.insert(t)
        if(t is CategorySub) daoCatSub.insert(t)
        if(t is Card) daoCard.insert(t)
        if(t is Income) daoIncome.insert(t)
        if(t is Spend) daoSpend.insert(t)
        if(t is SpendCash) daoSpendCash.insert(t)
        if(t is SpendCard) daoSpendCard.insert(t)
        if(t is IOKRW) daoIOKRW.insert(t)
        if(t is DairyKRW) daoDairyKRW.insert(t)
    }

    @WorkerThread
    fun <T> update(t: T) {
        if(t is Group) daoGroup.update(t)
        if(t is Account) daoAccount.update(t)
        if(t is CategoryMain) daoCatMain.update(t)
        if(t is CategorySub) daoCatSub.update(t)
        if(t is Card) daoCard.update(t)
        if(t is Income) daoIncome.update(t)
        if(t is Spend) daoSpend.update(t)
        if(t is SpendCash) daoSpendCash.update(t)
        if(t is SpendCard) daoSpendCard.update(t)
        if(t is IOKRW) daoIOKRW.update(t)
        if(t is DairyKRW) daoDairyKRW.update(t)
    }

    @WorkerThread
    fun <T> delete(t: T) {
        if(t is Group) daoGroup.delete(t)
        if(t is Account) daoAccount.delete(t)
        if(t is CategoryMain) daoCatMain.delete(t)
        if(t is CategorySub) daoCatSub.delete(t)
        if(t is Card) daoCard.delete(t)
        if(t is Income) daoIncome.delete(t)
        if(t is Spend) daoSpend.delete(t)
        if(t is SpendCash) daoSpendCash.delete(t)
        if(t is SpendCard) daoSpendCard.delete(t)
        if(t is IOKRW) daoIOKRW.delete(t)
        if(t is DairyKRW) daoDairyKRW.delete(t)
    }
}