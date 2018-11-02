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

    @WorkerThread
    fun <T> insert(t: T) {
        if(t is Group) daoGroup.insert(t)
        if(t is Account) daoAccount.insert(t)
        if(t is CategoryMain) daoCategoryMain.insert(t)
        if(t is CategorySub) daoCategorySub.insert(t)
        if(t is Card) daoCard.insert(t)
    }
}