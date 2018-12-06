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

    var allGroups: LiveData<List<Group>> = daoGroup.getAll()
    var allAccounts: LiveData<List<Account>> = daoAccount.getAll()
    var allCatMains: LiveData<List<CategoryMain>> = daoCatMain.getAll()
    var allCatSubs: LiveData<List<CategorySub>> = daoCatSub.getAll()
    var allCards: LiveData<List<Card>> = daoCard.getAll()

    fun getGroup(id: Int?): LiveData<Group> = daoGroup.get(id)
    fun getAccount(id: Int?): LiveData<Account> = daoAccount.get(id)
    fun getAccountByCode(code: String?): LiveData<Account> = daoAccount.getByCode(code)
    fun getCatMain(id: Int?): LiveData<CategoryMain> = daoCatMain.get(id)
    fun getCatMainByKind(kind: String?): LiveData<List<CategoryMain>> = daoCatMain.getByKind(kind)
    fun getCatMainBySub(id_sub: Int?): LiveData<CategoryMain> = daoCatMain.getBySub(id_sub)
    fun getCardByCode(code: String?): LiveData<Card> = daoCard.getByCode(code)
    fun getIncomes(date: String?): LiveData<List<Income>> = daoIncome.get(date)
    fun getCatSub(id: Int?): LiveData<CategorySub> = daoCatSub.get(id)
    fun getCatSubsByMain(nameOfMain: String?): LiveData<List<CategorySub>> = daoCatSub.getByMain(nameOfMain)
    fun getCatSubsByMain(id_main: Int?): LiveData<List<CategorySub>> = daoCatSub.getByMain(id_main)
    fun getSpends(date: String?): LiveData<List<Spend>> = daoSpend.get(date)

    @WorkerThread
    fun <T> insert(t: T) {
        if(t is Group) daoGroup.insert(t)
        if(t is Account) daoAccount.insert(t)
        if(t is CategoryMain) daoCatMain.insert(t)
        if(t is CategorySub) daoCatSub.insert(t)
        if(t is Card) daoCard.insert(t)
    }

    @WorkerThread
    fun <T> update(t: T) {
        if(t is Group) daoGroup.update(t)
        if(t is Account) daoAccount.update(t)
        if(t is CategoryMain) daoCatMain.update(t)
        if(t is CategorySub) daoCatSub.update(t)
        if(t is Card) daoCard.update(t)
    }

    @WorkerThread
    fun <T> delete(t: T) {
        if(t is Group) daoGroup.delete(t)
        if(t is Account) daoAccount.delete(t)
        if(t is CategoryMain) daoCatMain.delete(t)
        if(t is CategorySub) daoCatSub.delete(t)
        if(t is Card) daoCard.delete(t)
    }
}