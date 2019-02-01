package com.example.watering.assetlog.viewmodel

import android.app.Application
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.watering.assetlog.R
import com.example.watering.assetlog.entities.*
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
    fun getGroupByName(name: String?) = repository.getGroupByName(name)
    fun getAccount(id: Int?) = repository.getAccount(id)
    fun getAccountByCode(code: String?) = repository.getAccountByCode(code)
    fun getAccountByNumber(number: String?) = repository.getAccountByNumber(number)
    fun getAccountsByGroup(id: Int?) = repository.getAccountsByGroup(id)
    fun getCatMain(id: Int?) = repository.getCatMain(id)
    fun getCatMainsByKind(kind: String?) = repository.getCatMainByKind(kind)
    fun getCatMainBySub(id_sub: Int?) = repository.getCatMainBySub(id_sub)
    fun getCatSub(id: Int?) = repository.getCatSub(id)
    fun getCatSub(nameOfSub:String?, nameOfMain:String?) = repository.getCatSub(nameOfSub, nameOfMain)
    fun getCatSubsByMain(nameOfMain: String?) = repository.getCatSubsByMain(nameOfMain)
    fun getCatSubsByMain(id_main: Int?) = repository.getCatSubsByMain(id_main)
    fun getSpends(date: String?) = repository.getSpends(date)
    fun getSpendCash(code: String?) = repository.getSpendCash(code)
    fun getSpendCard(code: String?) = repository.getSpendCard(code)
    fun getIncomes(date: String?) = repository.getIncomes(date)
    fun getCardByCode(code: String?) = repository.getCardByCode(code)
    fun getCardByNumber(number: String?) = repository.getCardByNumber(number)
    fun getLastSpendCode(date: String?) = repository.getLastSpendCode(date)
    fun getLogs(id_account: Int?) = repository.getLogs(id_account)

    fun loadingIOKRW(id_account: Int?, date: String?): LiveData<IOKRW> {
        return Transformations.switchMap(getPreviousEvaluationOfKRW(id_account, date)) { previousEvaluation ->
            Transformations.switchMap(sumOfSpendCashForDate(id_account, date)) { sumOfSpendsCash ->
                Transformations.switchMap(sumOfSpendCardForDate(id_account, date)) { sumOfSpendsCard ->
                    Transformations.switchMap(sumOfIncomeForDate(id_account, date)) { sumOfIncome ->
                        Transformations.map(getIOKRW(id_account, date)) { io ->
                            if(io == null) {
                                val new = IOKRW()
                                new.date = date
                                new.evaluationKRW = previousEvaluation - sumOfSpendsCard - sumOfSpendsCash + sumOfIncome
                                new.account = id_account
                                new.spendCash = sumOfSpendsCash
                                new.spendCard = sumOfSpendsCard
                                new.income = sumOfIncome
                                new
                            } else {
                                io.evaluationKRW = previousEvaluation - sumOfSpendsCard - sumOfSpendsCash + sumOfIncome - io.output!! + io.input!!
                                io.spendCash = sumOfSpendsCash
                                io.spendCard = sumOfSpendsCard
                                io.income = sumOfIncome
                                io
                            }
                        }
                    }
                }
            }
        }
    }
    fun loadingIOForeign(id_account: Int?, date: String?, currency: Int?): LiveData<IOForeign> {
        return Transformations.switchMap(getPreviousEvaluationKRWOfForeign(id_account, date, currency)) { previousEvaluation ->
            Transformations.map(getIOForeign(id_account, date, currency)) { io ->
                if(io == null) {
                    val new = IOForeign()
                    new.account = id_account
                    new.currency = currency
                    new.date = date
                    new.evaluationKRW = previousEvaluation
                    new
                } else {
                    io.evaluationKRW = previousEvaluation + io.inputKRW!! - io.outputKRW!!
                    io
                }
            }
        }
    }
    fun loadingDairyKRW(id_account: Int?, date: String?): LiveData<DairyKRW> {
        return Transformations.switchMap(getDairyKRW(id_account, date)) { dairy ->
            Transformations.switchMap(calculatePrincipalOfKRW(id_account, date)) { principal ->
                Transformations.map(calculateRateOfKRW(id_account, date)) { rate ->
                    if(dairy == null) {
                        val new = DairyKRW()
                        new.principalKRW = principal
                        new.rate = rate
                        new.account = id_account
                        new.date = date
                        new
                    } else {
                        dairy.principalKRW = principal
                        dairy.rate = rate
                        dairy
                    }
                }
            }
        }
    }
    fun loadingDairyForeign(id_account: Int?, date: String?, currency: Int?): LiveData<DairyForeign> {
        return Transformations.switchMap(getDairyForeign(id_account, date, currency)) { dairy ->
            Transformations.switchMap(calculatePrincipalOfForeign(id_account, date, currency)) { principal ->
                Transformations.switchMap(calculatePrincipalKRWOfForeign(id_account, date, currency)) { principal_krw ->
                    Transformations.map(calculateRateOfForeign(id_account, date, currency)) { rate ->
                        if(dairy == null) {
                            val new = DairyForeign()
                            new.principal = principal
                            new.principalKRW = principal_krw
                            new.rate = rate
                            new.account = id_account
                            new.date = date
                            new
                        } else {
                            dairy.principal = principal
                            dairy.principalKRW = principal_krw
                            dairy.rate = rate
                            dairy
                        }
                    }
                }
            }
        }
    }
    fun loadingDairyTotal(id_account: Int?, date: String?): LiveData<DairyTotal> {
        return Transformations.switchMap(getLastDairyForeign(id_account, date)) { listOf_last_dairy_foreign ->
            Transformations.switchMap(getLastIOForeign(id_account, date)) { listOf_last_io_foreign ->
                Transformations.switchMap(getLastDairyKRW(id_account, date)) { last_dairy_krw ->
                    Transformations.switchMap(getLastIOKRW(id_account, date)) { last_io_krw ->
                        Transformations.map(getDairyTotal(id_account, date)) { dairy_total ->
                            var principal = 0
                            var evaluation = 0
                            var rate = 0.0

                            last_dairy_krw?.run { principal = principalKRW!! }
                            last_io_krw?.run { evaluation = evaluationKRW!! }

                            listOf_last_dairy_foreign.forEach { principal += it.principalKRW!! }
                            listOf_last_io_foreign.forEach { evaluation += it.evaluationKRW!!.toInt() }

                            if(principal != 0 && evaluation != 0) rate = evaluation.toDouble() / principal * 100 - 100

                            if(dairy_total == null) {
                                val new = DairyTotal()
                                new.account = id_account
                                new.date = date
                                new.evaluationKRW = evaluation
                                new.principalKRW = principal
                                new.rate = rate
                                new
                            } else {
                                dairy_total.evaluationKRW = evaluation
                                dairy_total.principalKRW = principal
                                dairy_total.rate = rate
                                dairy_total
                            }
                        }
                    }
                }
            }
        }
    }

    private fun getPreviousEvaluationOfKRW(id_account:Int?, date:String?): LiveData<Int> {
        val before = ModelCalendar.calendarToStr(ModelCalendar.changeDate(date, -1))

        return Transformations.map(getLastIOKRW(id_account, before)) { last ->
            if(last == null) 0 else last.evaluationKRW
        }
    }
    private fun getPreviousEvaluationKRWOfForeign(id_account:Int?, date:String?, currency: Int?): LiveData<Double> {
        val before = ModelCalendar.calendarToStr(ModelCalendar.changeDate(date, -1))

        return Transformations.map(getLastIOForeign(id_account, before, currency)) { last ->
            if(last == null) 0.0 else last.evaluationKRW
        }
    }

    private fun getIOKRW(id_account: Int?, date: String?) = repository.getIOKRW(id_account, date)
    private fun getIOForeign(id_account: Int?, date: String?, currency: Int?) = repository.getIOForeign(id_account, date, currency)
    private fun getDairyKRW(id_account: Int?, date: String?) = repository.getDairyKRW(id_account, date)
    private fun getDairyForeign(id_account: Int?, date: String?, currency: Int?) = repository.getDairyForeign(id_account, date, currency)
    private fun getDairyTotal(id_account: Int?, date: String?) = repository.getDairyTotal(id_account, date)

    private fun getLastIOKRW(id_account: Int?, date: String?) = repository.getLastIOKRW(id_account, date)
    private fun getLastIOForeign(id_account: Int?, date: String?, currency: Int?) = repository.getLastIOForeign(id_account, date, currency)
    private fun getLastDairyKRW(id_account: Int?, date: String?) = repository.getLastDairyKRW(id_account, date)
    private fun getLastIOForeign(id_account: Int?, date: String?) = repository.getLastIOForeign(id_account, date)
    private fun getLastDairyForeign(id_account: Int?, date: String?) = repository.getLastDairyForeign(id_account, date)

    private fun sumOfSpendCashForDate(id_account:Int?, date: String?) = repository.sumOfSpendCashForDate(id_account, date)
    private fun sumOfSpendCardForDate(id_account:Int?, date: String?) = repository.sumOfSpendCardForDate(id_account, date)
    private fun sumOfIncomeForDate(id_account:Int?, date: String?) = repository.sumOfIncomeForDate(id_account, date)
    private fun sumOfInputOfKRWUntilDate(id_account: Int?, date: String?) = repository.sumOfInputOfKRWUntilDate(id_account, date)
    private fun sumOfOutputOfKRWUntilDate(id_account: Int?, date: String?) = repository.sumOfOutputOfKRWUntilDate(id_account, date)
    private fun sumOfInputOfForeignUntilDate(id_account: Int?, date: String?, currency: Int?) = repository.sumOfInputOfForeignUntilDate(id_account, date, currency)
    private fun sumOfOutputOfForeignUntilDate(id_account: Int?, date: String?, currency: Int?) = repository.sumOfOutputOfForeignUntilDate(id_account, date, currency)
    private fun sumOfInputKRWOfForeignUntilDate(id_account: Int?, date: String?, currency: Int?) = repository.sumOfInputKRWOfForeignUntilDate(id_account, date, currency)
    private fun sumOfOutputKRWOfForeignUntilDate(id_account: Int?, date: String?, currency: Int?) = repository.sumOfOutputKRWOfForeignUntilDate(id_account, date, currency)
    private fun sumOfIncomeUntilDate(id_account: Int?, date: String?) = repository.sumOfIncomeUntilDate(id_account, date)
    private fun sumOfSpendCardUntilDate(id_account: Int?, date: String?) = repository.sumOfSpendCardUntilDate(id_account, date)
    private fun sumOfSpendCashUntilDate(id_account: Int?, date: String?) = repository.sumOfSpendCashUntilDate(id_account, date)

    private fun calculatePrincipalOfKRW(id_account: Int?, date: String?): LiveData<Int> {
       return Transformations.switchMap(sumOfInputOfKRWUntilDate(id_account, date)) { sum_input ->
           Transformations.switchMap(sumOfIncomeUntilDate(id_account, date)) { sum_income ->
               Transformations.switchMap(sumOfOutputOfKRWUntilDate(id_account, date)) { sum_output ->
                   Transformations.switchMap(sumOfSpendCardUntilDate(id_account, date)) { sum_card ->
                       Transformations.map(sumOfSpendCashUntilDate(id_account, date)) { sum_cash ->
                           sum_input + sum_income - sum_output - sum_card - sum_cash
                       }
                   }
               }
           }
       }
    }
    private fun calculatePrincipalOfForeign(id_account: Int?, date: String?, currency: Int?): LiveData<Double> {
        return Transformations.switchMap(sumOfInputOfForeignUntilDate(id_account, date, currency)) { sum_input ->
            Transformations.map(sumOfOutputOfForeignUntilDate(id_account, date, currency)) { sum_output ->
                var input = 0.0
                var output = 0.0
                sum_input?.let { input = it }
                sum_output?.let { output = it }

                input - output
            }
        }
    }
    private fun calculatePrincipalKRWOfForeign(id_account: Int?, date: String?, currency: Int?): LiveData<Int> {
        return Transformations.switchMap(sumOfInputKRWOfForeignUntilDate(id_account, date, currency)) { sum_input_krw ->
            Transformations.map(sumOfOutputKRWOfForeignUntilDate(id_account, date, currency)) { sum_output_krw ->
                var input = 0
                var output = 0

                sum_input_krw?.let { input = it }
                sum_output_krw?.let { output = it }

                input - output
            }
        }
    }
    private fun calculateRateOfKRW(id_account: Int?, date: String?): LiveData<Double> {
        return Transformations.switchMap(getIOKRW(id_account, date)) { io ->
            Transformations.switchMap(getLastIOKRW(id_account, date)) { lastIO ->
                Transformations.map(calculatePrincipalOfKRW(id_account, date)) { principal ->
                    // 해당일에 직접 입력한 평가액이 있는 경우를 대비
                    if(io == null) {
                        if(lastIO == null) 0.0 else lastIO.evaluationKRW!!.toDouble() / principal * 100 - 100
                    }
                    else io.evaluationKRW!!.toDouble() / principal * 100 - 100
                }
            }
        }
    }
    private fun calculateRateOfForeign(id_account: Int?, date: String?, currency: Int?): LiveData<Double> {
        return Transformations.switchMap(getIOForeign(id_account, date, currency)) { io ->
            Transformations.switchMap(getLastIOForeign(id_account, date, currency)) { lastIO ->
                Transformations.map(calculatePrincipalKRWOfForeign(id_account, date, currency)) { principal ->
                    if(io == null) {
                        if(lastIO == null) 0.0 else lastIO.evaluationKRW!! / principal * 100 - 100
                    }
                    else io.evaluationKRW!! / principal * 100 - 100
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }
}