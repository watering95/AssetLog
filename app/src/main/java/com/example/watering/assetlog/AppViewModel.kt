package com.example.watering.assetlog

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.watering.assetlog.entities.Account
import com.example.watering.assetlog.entities.CategoryMain
import com.example.watering.assetlog.entities.CategorySub
import com.example.watering.assetlog.entities.Group
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class AppViewModel(application: Application) : AndroidViewModel(application) {
    private var parentJob = Job()
    private val coroutineContext = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)

    private val repository = AppRepository(application, scope)

    val allGroups: LiveData<List<Group>> = repository.allGroups
    val allAccounts: LiveData<List<Account>> = repository.allAccounts
    val allCategoryMains: LiveData<List<CategoryMain>> = repository.allCategoryMains
    val allCategorySubs: LiveData<List<CategorySub>> = repository.allCategorySubs

    fun <T> insert(t: T) = scope.launch(Dispatchers.IO) {
        repository.insert(t)
    }

    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }
}