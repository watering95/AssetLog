package com.example.watering.assetlog.viewmodel

import android.app.Application
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.AndroidViewModel
import com.example.watering.assetlog.R
import com.example.watering.assetlog.model.AppRepository
import kotlinx.coroutines.*

open class ViewModelApp(application: Application) : AndroidViewModel(application) {
    private var parentJob = Job()
    private val coroutineContext = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)

    private val repository = AppRepository(application, scope)

    val allGroups = repository.allGroups
    val allAccounts = repository.allAccounts
    val allCatMains = repository.allCatMains
    val allCatSubs = repository.allCatSubs
    val allCards = repository.allCards

    fun getGroup(id: Int?) = repository.getGroup(id)
    fun getAccount(id: Int?) = repository.getAccount(id)
    fun getAccountByCode(code: String?) = repository.getAccountByCode(code)
    fun getCatMain(id: Int?) = repository.getCatMain(id)
    fun getCatMainsByKind(kind: String?) = repository.getCatMainByKind(kind)
    fun getCatMainBySub(id_sub: Int?) = repository.getCatMainBySub(id_sub)
    fun getCatSub(id: Int?) = repository.getCatSub(id)
    fun getCatSubsByMain(nameOfMain: String?) = repository.getCatSubsByMain(nameOfMain)
    fun getCatSubsByMain(id_main: Int?) = repository.getCatSubsByMain(id_main)
    fun getSpends(date: String?) = repository.getSpends(date)
    fun getIncomes(date: String?) = repository.getIncomes(date)
    fun getCardByCode(code: String?) = repository.getCardByCode(code)

    fun <T> insert(t: T) = scope.launch(Dispatchers.IO) { repository.insert(t) }

    fun <T> update(t: T) = scope.launch(Dispatchers.IO) { repository.update(t) }

    fun <T> delete(t: T) = scope.launch(Dispatchers.IO) { repository.delete(t) }

    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }

    fun replaceFragement(fragmentManager: FragmentManager, fragment: Fragment) {
        fragmentManager.run {
            val transaction = beginTransaction()
            transaction.replace(R.id.frame_main, fragment).addToBackStack(null).commit()
        }
    }
}