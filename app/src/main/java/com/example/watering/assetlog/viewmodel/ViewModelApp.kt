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
    fun getAccountByNumber(number: String?) = repository.getAccountByNumber(number)
    fun getCatMain(id: Int?) = repository.getCatMain(id)
    fun getCatMainsByKind(kind: String?) = repository.getCatMainByKind(kind)
    fun getCatMainBySub(id_sub: Int?) = repository.getCatMainBySub(id_sub)
    fun getCatSub(id: Int?) = repository.getCatSub(id)
    fun getCatSub(nameOfSub:String?, nameOfMain:String?) = repository.getCatSub(nameOfSub, nameOfMain)
    fun getCatSubsByMain(nameOfMain: String?) = repository.getCatSubsByMain(nameOfMain)
    fun getCatSubsByMain(id_main: Int?) = repository.getCatSubsByMain(id_main)
    fun getSpendByCode(code: String?) = repository.getSpendByCode(code)
    fun getSpends(date: String?) = repository.getSpends(date)
    fun getSpendCash(code: String?) = repository.getSpendCash(code)
    fun getSpendCard(code: String?) = repository.getSpendCard(code)
    fun getIncomes(date: String?) = repository.getIncomes(date)
    fun getCardByCode(code: String?) = repository.getCardByCode(code)
    fun getCardByNumber(number: String?) = repository.getCardByNumber(number)
    fun getIOKRW(id_account: Int?, date: String?) = repository.getIOKRW(id_account, date)
    fun getDairyKRW(id_account: Int?, date: String?) = repository.getDairyKRW(id_account, date)
    fun getLastSpendCode(date: String?) = repository.getLastSpendCode(date)
    fun getLastIOKRW(id_account: Int?, date: String?) = repository.getLastIOKRW(id_account, date)

    private fun sumOfSpendsCash(id_account:Int?, date: String?) = repository.sumOfSpendsCash(id_account, date)
    private fun sumOfSpendsCard(id_account:Int?, date: String?) = repository.sumOfSpendsCard(id_account, date)
    private fun sumOfIncome(id_account:Int?, date: String?) = repository.sumOfIncome(id_account, date)
    private fun sum(column:String?, id_account:Int?, date: String?) = repository.sum(column, id_account, date)

    fun <T> insert(t: T) = scope.launch(Dispatchers.IO) { repository.insert(t) }
    fun <T> update(t: T) = scope.launch(Dispatchers.IO) { repository.update(t) }
    fun <T> delete(t: T) = scope.launch(Dispatchers.IO) { repository.delete(t) }

    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }

    fun replaceFragment(fragmentManager: FragmentManager, fragment: Fragment) {
        fragmentManager.run {
            val transaction = beginTransaction()
            transaction.replace(R.id.frame_main, fragment).addToBackStack(null).commit()
        }
    }
}