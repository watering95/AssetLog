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
    private val daoCategoryMain = db.daoCategoryMain()
    private val daoCategorySub = db.daoCategorySub()
    private val daoCard = db.daoCard()

    var allGroups: LiveData<List<Group>> = daoGroup.getAll()
    var allAccounts: LiveData<List<Account>> = daoAccount.getAll()
    var allCategoryMains: LiveData<List<CategoryMain>> = daoCategoryMain.getAll()
    var allCategorySubs: LiveData<List<CategorySub>> = daoCategorySub.getAll()
    var allCards: LiveData<List<Card>> = daoCard.getAll()

    fun getGroup(id:Int?): LiveData<Group> = daoGroup.get(id)
    fun getAccount(id:Int?): LiveData<Account> = daoAccount.get(id)
    fun getCategoryMain(id:Int?): LiveData<CategoryMain> = daoCategoryMain.get(id)

    @WorkerThread
    fun <T> insert(t: T) {
        if(t is Group) daoGroup.insert(t)
        if(t is Account) daoAccount.insert(t)
        if(t is CategoryMain) daoCategoryMain.insert(t)
        if(t is CategorySub) daoCategorySub.insert(t)
        if(t is Card) daoCard.insert(t)
    }

    @WorkerThread
    fun <T> update(t: T) {
        if(t is Group) daoGroup.update(t)
        if(t is Account) daoAccount.update(t)
        if(t is CategoryMain) daoCategoryMain.update(t)
        if(t is CategorySub) daoCategorySub.update(t)
        if(t is Card) daoCard.update(t)
    }

    @WorkerThread
    fun <T> delete(t: T) {
        if(t is Group) daoGroup.delete(t)
        if(t is Account) daoAccount.delete(t)
        if(t is CategoryMain) daoCategoryMain.delete(t)
        if(t is CategorySub) daoCategorySub.delete(t)
        if(t is Card) daoCard.delete(t)
    }
}