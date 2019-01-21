package com.example.watering.assetlog.viewmodel

import android.app.Application
import androidx.databinding.Bindable
import androidx.lifecycle.*
import com.example.watering.assetlog.BR

class ViewModelHome(application:Application) : ObservableViewModel(application) {
    var total_principal: Int = 0
    @Bindable get() {
        return field
    }
    set(value) {
        field = value
        notifyPropertyChanged(BR.listOfMain)
    }

    var total_evaluation: Int = 0
    @Bindable get() {
        return field
    }
    set(value) {
        field = value
        notifyPropertyChanged(BR.listOfMain)
    }

    var total_rate: Double = 0.0
    @Bindable get() {
        return field
    }
    set(value) {
        field = value
        notifyPropertyChanged(BR.listOfMain)
    }
}