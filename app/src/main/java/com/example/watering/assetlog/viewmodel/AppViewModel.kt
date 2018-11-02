package com.example.watering.assetlog.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.watering.assetlog.model.AppRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

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

    fun <T> insert(t: T) = scope.launch(Dispatchers.IO) {
        repository.insert(t)
    }

    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }
}