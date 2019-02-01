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
        return Transformations.switchMap(calculateEvaluationInKRW(id_account, date)) { evaluation ->
            Transformations.switchMap(sumOfSpendCashInDate(id_account, date)) { sumOfSpendsCash ->
                Transformations.switchMap(sumOfSpendCardInDate(id_account, date)) { sumOfSpendsCard ->
                    Transformations.switchMap(sumOfIncomeInDate(id_account, date)) { sumOfIncome ->
                        Transformations.map(getIOKRW(id_account, date)) { io ->
                            if(io == null) {
                                val new = IOKRW()
                                new.date = date
                                new.evaluationKRW = evaluation - sumOfSpendsCard - sumOfSpendsCash + sumOfIncome
                                new.account = id_account
                                new.spendCash = sumOfSpendsCash
                                new.spendCard = sumOfSpendsCard
                                new.income = sumOfIncome
                                new
                            } else {
                                io.evaluationKRW = evaluation - sumOfSpendsCard - sumOfSpendsCash + sumOfIncome - io.output!! + io.input!!
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
        return Transformations.switchMap(calculateEvaluationKRWInForeign(id_account, date, currency)) { evaluation ->
            Transformations.map(getIOForeign(id_account, date, currency)) { io ->
                if(io == null) {
                    val new = IOForeign()
                    new.account = id_account
                    new.currency = currency
                    new.date = date
                    new.evaluationKRW = evaluation
                    new
                } else {
                    io.evaluationKRW = evaluation
                    io
                }
            }
        }
    }
    fun loadingDairyKRW(id_account: Int?, date: String?): LiveData<DairyKRW> {
        return Transformations.switchMap(getDairyKRW(id_account, date)) { dairy ->
            Transformations.switchMap(calculatePrincipalInKRW(id_account, date)) { principal ->
                Transformations.map(calculateRateInKRW(id_account, date)) { rate ->
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
            Transformations.switchMap(calculatePrincipalInForeign(id_account, date, currency)) { principal ->
                Transformations.switchMap(calculatePrincipalKRWInForeign(id_account, date, currency)) { principal_krw ->
                    Transformations.map(calculateRateInForeign(id_account, date, currency)) { rate ->
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
        return Transformations.switchMap(getLastDairyForeign(id_account, date)) { list_dairy_foreign ->
            Transformations.switchMap(getLastIOForeign(id_account, date)) { list_io_foreign ->
                Transformations.switchMap(getLastDairyKRW(id_account, date)) { dairy_krw ->
                    Transformations.switchMap(getLastIOKRW(id_account, date)) { io_krw ->
                        Transformations.map(getDairyTotal(id_account, date)) { dairy_total ->
                            var principal = 0
                            var evaluation = 0
                            var rate = 0.0

                            dairy_krw?.run { principal = principalKRW!! }
                            io_krw?.run { evaluation = evaluationKRW!! }

                            list_dairy_foreign.forEach { principal += it.principalKRW!! }
                            list_io_foreign.forEach { evaluation += it.evaluationKRW!!.toInt() }

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

    private fun sumOfSpendCashInDate(id_account:Int?, date: String?) = repository.sumOfSpendCashInDate(id_account, date)
    private fun sumOfSpendCardInDate(id_account:Int?, date: String?) = repository.sumOfSpendCardInDate(id_account, date)
    private fun sumOfIncomeInDate(id_account:Int?, date: String?) = repository.sumOfIncomeInDate(id_account, date)
    private fun sumOfInputInKRWUntilDate(id_account: Int?, date: String?) = repository.sumOfInputInKRWUntilDate(id_account, date)
    private fun sumOfOutputInKRWUntilDate(id_account: Int?, date: String?) = repository.sumOfOutputInKRWUntilDate(id_account, date)
    private fun sumOfInputInForeignUntilDate(id_account: Int?, date: String?, currency: Int?) = repository.sumOfInputInForeignUntilDate(id_account, date, currency)
    private fun sumOfOutputInForeignUntilDate(id_account: Int?, date: String?, currency: Int?) = repository.sumOfOutputInForeignUntilDate(id_account, date, currency)
    private fun sumOfInputKRWInForeignUntilDate(id_account: Int?, date: String?, currency: Int?) = repository.sumOfInputKRWInForeignUntilDate(id_account, date, currency)
    private fun sumOfOutputKRWInForeignUntilDate(id_account: Int?, date: String?, currency: Int?) = repository.sumOfOutputKRWInForeignUntilDate(id_account, date, currency)
    private fun sumOfIncomeUntilDate(id_account: Int?, date: String?) = repository.sumOfIncomeUntilDate(id_account, date)
    private fun sumOfSpendCardUntilDate(id_account: Int?, date: String?) = repository.sumOfSpendCardUntilDate(id_account, date)
    private fun sumOfSpendCashUntilDate(id_account: Int?, date: String?) = repository.sumOfSpendCashUntilDate(id_account, date)

    private fun calculatePrincipalInKRW(id_account: Int?, date: String?): LiveData<Int> {
       return Transformations.switchMap(sumOfInputInKRWUntilDate(id_account, date)) { sum_input ->
           Transformations.switchMap(sumOfIncomeUntilDate(id_account, date)) { sum_income ->
               Transformations.switchMap(sumOfOutputInKRWUntilDate(id_account, date)) { sum_output ->
                   Transformations.switchMap(sumOfSpendCardUntilDate(id_account, date)) { sum_card ->
                       Transformations.map(sumOfSpendCashUntilDate(id_account, date)) { sum_cash ->
                           val principal = sum_input + sum_income - sum_output - sum_card - sum_cash
                           principal
                       }
                   }
               }
           }
       }
    }
    private fun calculatePrincipalInForeign(id_account: Int?, date: String?, currency: Int?): LiveData<Double> {
        return Transformations.switchMap(sumOfInputInForeignUntilDate(id_account, date, currency)) { sum_input ->
            Transformations.map(sumOfOutputInForeignUntilDate(id_account, date, currency)) { sum_output ->
                var input = 0.0
                var output = 0.0
                sum_input?.let { input = it }
                sum_output?.let { output = it }

                val principal = input - output
                principal
            }
        }
    }
    private fun calculatePrincipalKRWInForeign(id_account: Int?, date: String?, currency: Int?): LiveData<Int> {
        return Transformations.switchMap(sumOfInputKRWInForeignUntilDate(id_account, date, currency)) { sum_input_krw ->
            Transformations.map(sumOfOutputKRWInForeignUntilDate(id_account, date, currency)) { sum_output_krw ->
                var input = 0
                var output = 0

                sum_input_krw?.let { input = it }
                sum_output_krw?.let { output = it }

                val result = input - output
                result
            }
        }
    }
    private fun calculateRateInKRW(id_account: Int?, date: String?): LiveData<Double> {
        return Transformations.switchMap(getIOKRW(id_account, date)) { io ->
            Transformations.switchMap(getLastIOKRW(id_account, date)) { lastIO ->
                Transformations.map(calculatePrincipalInKRW(id_account, date)) { principal ->
                    val rate = if(io == null) {
                        if(lastIO == null) 0.0 else lastIO.evaluationKRW!!.toDouble() / principal * 100 - 100
                    }
                    else io.evaluationKRW!!.toDouble() / principal * 100 - 100
                    rate
                }
            }
        }
    }
    private fun calculateRateInForeign(id_account: Int?, date: String?, currency: Int?): LiveData<Double> {
        return Transformations.switchMap(getIOForeign(id_account, date, currency)) { io ->
            Transformations.switchMap(getLastIOForeign(id_account, date, currency)) { lastIO ->
                Transformations.map(calculatePrincipalKRWInForeign(id_account, date, currency)) { principal ->
                    val rate = if(io == null) {
                        if(lastIO == null) 0.0 else lastIO.evaluationKRW!! / principal * 100 - 100
                    }
                    else io.evaluationKRW!! / principal * 100 - 100
                    rate
                }
            }
        }
    }
    private fun calculateEvaluationInKRW(id_account:Int?, date:String?): LiveData<Int> {
        val before = ModelCalendar.calendarToStr(ModelCalendar.changeDate(date, -1))

        return Transformations.map(getLastIOKRW(id_account, before)) { last ->
            val evaluation = if(last == null) 0 else last.evaluationKRW
            evaluation
        }
    }
    private fun calculateEvaluationKRWInForeign(id_account:Int?, date:String?, currency: Int?): LiveData<Double> {
        val before = ModelCalendar.calendarToStr(ModelCalendar.changeDate(date, -1))

        return Transformations.map(getLastIOForeign(id_account, before, currency)) { last ->
            val evaluation = if(last == null) 0.0 else last.evaluationKRW
            evaluation
        }
    }

    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }
}