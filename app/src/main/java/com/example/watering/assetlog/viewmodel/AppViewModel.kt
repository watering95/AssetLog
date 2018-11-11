package com.example.watering.assetlog.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.watering.assetlog.model.AppRepository
import kotlinx.coroutines.*

class AppViewModel(application: Application) : AndroidViewModel(application) {
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

    fun <T> insert(t: T) = scope.launch(Dispatchers.IO) { repository.insert(t) }

    fun <T> update(t: T) = scope.launch(Dispatchers.IO) { repository.update(t) }

    fun <T> delete(t: T) = scope.launch(Dispatchers.IO) { repository.delete(t) }

    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }
}