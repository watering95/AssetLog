package com.example.watering.assetlog.work

import android.content.Context
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.watering.assetlog.entities.DairyKRW
import com.example.watering.assetlog.model.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class ModifyDairyKRWWorker(context: Context, params: WorkerParameters): Worker(context, params) {
    private var parentJob = Job()
    private val coroutineContext = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)
    private val db = AppDatabase.getDatabase(context, scope)
    private val daoIOKRW = db.daoIOKRW()
    private val daoDairyKRW = db.daoDairyKRW()

    override fun doWork(): Result {
        val id_account = inputData.getInt("ID",0)
        val date = inputData.getString("DATE")

        modifyDairyKRW(id_account, date)

        return Result.success()
    }

    fun modifyDairyKRW(id_account: Int?, date: String?) {
        val principal = Transformations.switchMap(daoIOKRW.sum("input", id_account, date)) { sum_input ->
            Transformations.switchMap(daoIOKRW.sum("income", id_account, date)) { sum_income ->
                Transformations.switchMap(daoIOKRW.sum("output", id_account, date)) {sum_output ->
                    Transformations.switchMap(daoIOKRW.sum("spend_card", id_account, date)) { sum_card ->
                        Transformations.map(daoIOKRW.sum("spend_cash", id_account, date)) { sum_cash ->
                            sum_input + sum_income - sum_output - sum_card - sum_cash
                        }
                    }
                }
            }
        }
        val rate = Transformations.switchMap(daoIOKRW.get(id_account, date)) { io ->
            Transformations.map(principal) { principal ->
                io.evaluation_krw!!.toDouble() / principal * 100 - 100
            }
        }
        Transformations.switchMap(daoDairyKRW.get(id_account, date)) { dairy ->
            Transformations.switchMap(principal) { principal ->
                Transformations.map(rate) { rate ->
                    if(dairy.id == null) {
                        val newDairy = DairyKRW()
                        newDairy.principal_krw = principal
                        newDairy.rate = rate
                        newDairy.account = id_account
                        newDairy.date = date
                        daoDairyKRW.insert(newDairy)
                    } else {
                        dairy.principal_krw = principal
                        dairy.rate = rate
                        daoDairyKRW.update(dairy)
                    }
                }
            }
        }
    }
}