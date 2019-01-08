package com.example.watering.assetlog.viewmodel

import android.app.Application
import androidx.databinding.Bindable
import com.example.watering.assetlog.BR

class ViewModelEditInoutKRW(application:Application) : ObservableViewModel(application) {

    var date = ""
    @Bindable get() {
        return field
    }
    set(value) {
        field = value
        notifyPropertyChanged(BR.date)
    }

    var principal = 0
    @Bindable get() {
        return field
    }
    set(value) {
        field = value
        notifyPropertyChanged(BR.principal)
    }

    var withdraw = 0
    @Bindable get() {
        return field
    }
    set(value) {
        field = value
        notifyPropertyChanged(BR.withdraw)
    }

    var evaluation = 0
    @Bindable get() {
        return field
    }
    set(value) {
        field = value
        notifyPropertyChanged(BR.evaluation)
    }

    var spend = 0
    @Bindable get() {
        return field
    }
    set(value) {
        field = value
        notifyPropertyChanged(BR.spend)
    }

    var income = 0
    @Bindable get() {
        return field
    }
    set(value) {
        field = value
        notifyPropertyChanged(BR.income)
    }

    var deposit = 0
    @Bindable get() {
        return field
    }
    set(value) {
        field = value
        notifyPropertyChanged(BR.deposit)
    }
}