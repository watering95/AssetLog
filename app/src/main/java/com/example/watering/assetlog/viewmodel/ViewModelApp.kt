package com.example.watering.assetlog.viewmodel

import android.app.Application
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.AndroidViewModel
import com.example.watering.assetlog.R
import com.example.watering.assetlog.model.AppRepository
import kotlinx.coroutines.*

class ViewModelApp(application: Application) : AndroidViewModel(application) {
    private var parentJob = Job()
    private val coroutineContext = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)

    private val repository = AppRepository(application, scope)

    val allGroups = repository.allGroups
    val allAccounts = repository.allAccounts
    val allCategoryMains = repository.allCategoryMains
    val allCategorySubs = repository.allCategorySubs
    val allCards = repository.allCards

    fun getGroup(id: Int?) = repository.getGroup(id)
    fun getAccount(id: Int?) = repository.getAccount(id)
    fun getCategoryMain(id: Int?) = repository.getCategoryMain(id)
    fun getSpends(date: String?) = repository.getSpends(date)
    fun getIncomes(date: String?) = repository.getIncomes(date)

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