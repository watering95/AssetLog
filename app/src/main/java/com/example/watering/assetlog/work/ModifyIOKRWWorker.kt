package com.example.watering.assetlog.work

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.watering.assetlog.entities.IOKRW
import com.example.watering.assetlog.model.AppDatabase
import com.example.watering.assetlog.model.ModelCalendar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class ModifyIOKRWWorker(context: Context, params: WorkerParameters): Worker(context, params) {
    private var parentJob = Job()
    private val coroutineContext = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)
    private val db = AppDatabase.getDatabase(context, scope)
    private val daoIOKRW = db.daoIOKRW()

    override fun doWork(): Result {
        val id_account = inputData.getInt("ID",0)
        val date = inputData.getString("DATE")

        modifyIOKRW(id_account, date)

        return Result.success()
    }

    private fun calculateEvaluation(id_account:Int?, date:String?):LiveData<Int> {
        val before = ModelCalendar.calendarToStr(ModelCalendar.changeDate(date, -1))

        return Transformations.switchMap(daoIOKRW.getLast(id_account, before)) { last ->
            val evaluation = last.evaluation_krw!!
            Transformations.map(daoIOKRW.get(id_account, date)) { io ->
                evaluation - io.spend_cash!! - io.spend_card!! + io.income!!
            }
        }
    }

    fun modifyIOKRW(id_account: Int?, date: String?) {
        Transformations.switchMap(calculateEvaluation(id_account, date)) { evaluation ->
            Transformations.map(daoIOKRW.get(id_account, date)) { io ->
                if(io.id == null) {
                    val newIO = IOKRW()
                    newIO.date = date
                    newIO.evaluation_krw = evaluation
                    newIO.account = id_account
                    daoIOKRW.insert(newIO)
                }
                else {
                    io.evaluation_krw = evaluation
                    daoIOKRW.update(io)
                }
            }
        }
    }
}