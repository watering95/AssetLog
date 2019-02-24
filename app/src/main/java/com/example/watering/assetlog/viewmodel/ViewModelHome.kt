package com.example.watering.assetlog.viewmodel

import android.app.Application
import androidx.databinding.Bindable
import androidx.lifecycle.*
import com.example.watering.assetlog.BR
import com.example.watering.assetlog.entities.Home
import com.example.watering.assetlog.model.ModelCalendar

class ViewModelHome(application:Application) : ObservableViewModel(application) {
    var list: LiveData<List<LiveData<Home>>> = MutableLiveData()
    @Bindable get() {
        return field
    }
    set(value) {
        field = value
        notifyPropertyChanged(BR.list)
    }

    var totalPrincipal: Int = 0
    @Bindable get() {
        return field
    }
    set(value) {
        field = value
        if(totalEvaluation != 0) totalRate = totalEvaluation.toDouble() / totalPrincipal * 100 - 100
        notifyPropertyChanged(BR.totalPrincipal)
    }

    var totalEvaluation: Int = 0
    @Bindable get() {
        return field
    }
    set(value) {
        field = value
        if(totalEvaluation != 0) totalRate = totalEvaluation.toDouble() / totalPrincipal * 100 - 100
        notifyPropertyChanged(BR.totalEvaluation)
    }

    var totalRate: Double = 0.0
    @Bindable get() {
        return field
    }
    set(value) {
        field = value
        notifyPropertyChanged(BR.totalRate)
    }

    var listOfGroup: LiveData<List<String?>> = Transformations.map(allGroups) { list -> listOf("전체") + list.map { it.name } }
    @Bindable get() {
        return field
    }
    set(value) {
        field = value
        notifyPropertyChanged(BR.listOfGroup)
    }

    var indexOfGroup = 0
    @Bindable get() {
        return field
    }
    set(value) {
        field = value
        val accounts = if(field == 0) allAccounts else {
            Transformations.switchMap(listOfGroup) { list ->
                Transformations.switchMap(getGroup(list[field])) { group ->
                    Transformations.map(getAccountsByGroup(group.id)) { accounts -> accounts }
                }
            }
        }
        list = Transformations.map(accounts) {
            it.map { account ->
                Transformations.map(loadingDairyTotal(account.id, ModelCalendar.getToday())) { dairy ->
                    val home = Home()
                    home.evaluationKRW = dairy.evaluationKRW
                    home.principalKRW = dairy.principalKRW
                    home.rate = dairy.rate
                    home.account = account.number
                    home.description = account.institute + " " + account.description
                    home
                 }
            }
        }
        notifyPropertyChanged(BR.indexOfGroup)
    }
}