package com.example.watering.assetlog.viewmodel

import android.app.Application
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.watering.assetlog.R
import com.example.watering.assetlog.entities.DairyKRW
import com.example.watering.assetlog.entities.IOKRW
import com.example.watering.assetlog.model.AppRepository
import com.example.watering.assetlog.model.ModelCalendar
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

    fun <T> insert(t: T) = scope.launch(Dispatchers.IO) { repository.insert(t) }
    fun <T> update(t: T) = scope.launch(Dispatchers.IO) { repository.update(t) }
    fun <T> delete(t: T) = scope.launch(Dispatchers.IO) { repository.delete(t) }

    fun close() = repository.close()

    fun replaceFragment(fragmentManager: FragmentManager, fragment: Fragment) {
        fragmentManager.run {
            val transaction = beginTransaction()
            transaction.replace(R.id.frame_main, fragment).addToBackStack(null).commit()
        }
    }

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
    fun getLastSpendCode(date: String?) = repository.getLastSpendCode(date)

    private fun getIOKRW(id_account: Int?, date: String?) = repository.getIOKRW(id_account, date)
    private fun getDairyKRW(id_account: Int?, date: String?) = repository.getDairyKRW(id_account, date)
    private fun getLastIOKRW(id_account: Int?, date: String?) = repository.getLastIOKRW(id_account, date)

    private fun sumOfSpendsCash(id_account:Int?, date: String?) = repository.sumOfSpendsCash(id_account, date)
    private fun sumOfSpendsCard(id_account:Int?, date: String?) = repository.sumOfSpendsCard(id_account, date)
    private fun sumOfIncome(id_account:Int?, date: String?) = repository.sumOfIncome(id_account, date)
    private fun sum(column:String?, id_account:Int?, date: String?) = repository.sum(column, id_account, date)

    fun modifyIOKRW(id_account: Int?, date: String?): LiveData<IOKRW> {
        return Transformations.switchMap(calculateEvaluation(id_account, date)) { evaluation ->
            Transformations.map(getIOKRW(id_account, date)) { io ->
                if(io == null) {
                    val newIO = IOKRW()
                    newIO.date = date
                    newIO.evaluation_krw = evaluation
                    newIO.account = id_account
                    newIO
                }
                else {
                    io.evaluation_krw = evaluation
                    io
                }
            }
        }
    }
    fun modifyDairyKRW(id_account: Int?, date: String?): LiveData<DairyKRW> {
        val principal = Transformations.switchMap(sum("input", id_account, date)) { sum_input ->
            Transformations.switchMap(sum("income", id_account, date)) { sum_income ->
                Transformations.switchMap(sum("output", id_account, date)) {sum_output ->
                    Transformations.switchMap(sum("spend_card", id_account, date)) { sum_card ->
                        Transformations.map(sum("spend_cash", id_account, date)) { sum_cash ->
                            sum_input + sum_income - sum_output - sum_card - sum_cash
                        }
                    }
                }
            }
        }

        val rate = Transformations.switchMap(getIOKRW(id_account, date)) { io ->
            Transformations.map(principal) { principal ->
                io.evaluation_krw!!.toDouble() / principal * 100 - 100
            }
        }

        return Transformations.switchMap(getDairyKRW(id_account, date)) { dairy ->
            Transformations.switchMap(principal) { principal ->
                Transformations.map(rate) { rate ->
                    if(dairy == null) {
                        val newDairy = DairyKRW()
                        newDairy.principal_krw = principal
                        newDairy.rate = rate
                        newDairy.account = id_account
                        newDairy.date = date
                        newDairy
                    } else {
                        dairy.principal_krw = principal
                        dairy.rate = rate
                        dairy
                    }
                }
            }
        }
    }
    private fun calculateEvaluation(id_account:Int?, date:String?): LiveData<Int> {
        val before = ModelCalendar.calendarToStr(ModelCalendar.changeDate(date, -1))

        return Transformations.switchMap(getLastIOKRW(id_account, before)) { last ->
            val evaluation = last.evaluation_krw
            Transformations.map(getIOKRW(id_account, date)) { io ->
                if(io != null) evaluation!! - io.spend_cash!! - io.spend_card!! + io.income!!
                else evaluation!!
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }
}