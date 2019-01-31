package com.example.watering.assetlog.viewmodel

import android.app.Application
import androidx.databinding.Bindable
import androidx.lifecycle.*
import com.example.watering.assetlog.BR
import com.example.watering.assetlog.entities.Home

class ViewModelHome(application:Application) : ObservableViewModel(application) {
    var list: List<Home> = listOf()
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
        notifyPropertyChanged(BR.totalRate)
    }

    var totalEvaluation: Int = 0
    @Bindable get() {
        return field
    }
    set(value) {
        field = value
        if(totalEvaluation != 0) totalRate = totalEvaluation.toDouble() / totalPrincipal * 100 - 100
        notifyPropertyChanged(BR.totalEvaluation)
        notifyPropertyChanged(BR.totalRate)
    }

    var totalRate: Double = 0.0
    @Bindable get() {
        return field
    }
    set(value) {
        field = value
        notifyPropertyChanged(BR.totalRate)
    }

    var listOfGroup: LiveData<List<String?>> = MutableLiveData<List<String?>>()
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
        notifyPropertyChanged(BR.indexOfGroup)
    }
}