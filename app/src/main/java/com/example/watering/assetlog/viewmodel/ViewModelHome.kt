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

    var total_principal: Int = 0
    @Bindable get() {
        return field
    }
    set(value) {
        field = value
        if(total_evaluation != 0) total_rate = total_evaluation.toDouble() / total_principal * 100 - 100
        notifyPropertyChanged(BR.total_principal)
        notifyPropertyChanged(BR.total_rate)
    }

    var total_evaluation: Int = 0
    @Bindable get() {
        return field
    }
    set(value) {
        field = value
        if(total_evaluation != 0) total_rate = total_evaluation.toDouble() / total_principal * 100 - 100
        notifyPropertyChanged(BR.total_evaluation)
        notifyPropertyChanged(BR.total_rate)
    }

    var total_rate: Double = 0.0
    @Bindable get() {
        return field
    }
    set(value) {
        field = value
        notifyPropertyChanged(BR.total_rate)
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