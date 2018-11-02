package com.example.watering.assetlog

import android.app.Application
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.watering.assetlog.entities.Account
import com.example.watering.assetlog.entities.CategoryMain
import com.example.watering.assetlog.entities.CategorySub
import com.example.watering.assetlog.entities.Group
import kotlinx.coroutines.CoroutineScope

class AppRepository(application: Application, scope:CoroutineScope) {
    val db = AppDatabase.getDatabase(application, scope)
    val daoGroup = db.daoGroup()
    val daoAccount = db.daoAccount()
    val daoCategoryMain = db.daoCategoryMain()
    val daoCategorySub = db.daoCategorySub()

    var allGroups: LiveData<List<Group>> = daoGroup.getAll()
    var allAccounts: LiveData<List<Account>> = daoAccount.getAll()
    var allCategoryMains: LiveData<List<CategoryMain>> = daoCategoryMain.getAll()
    var allCategorySubs: LiveData<List<CategorySub>> = daoCategorySub.getAll()

    @WorkerThread
    fun <T> insert(t: T) {
        if(t is Group) daoGroup.insert(t)
        if(t is Account) daoAccount.insert(t)
    }
}