package com.example.watering.assetlog.viewmodel

import android.app.Application
import androidx.databinding.Bindable
import androidx.lifecycle.*
import com.example.watering.assetlog.BR
import com.example.watering.assetlog.entities.Spend

class ViewModelEditInoutForeign(application:Application) : ObservableViewModel(application) {

    var date = ""
    @Bindable get() {
        return field
    }
    set(value) {
        field = value
        notifyPropertyChanged(BR.date)
    }

    var deposit = 0
    @Bindable get() {
        return field
    }
    set(value) {
        field = value
        notifyPropertyChanged(BR.deposit)
    }

    var deposit_krw = 0
    @Bindable get() {
        return field
    }
    set(value) {
        field = value
        notifyPropertyChanged(BR.deposit_krw)
    }

    var withdraw = 0
    @Bindable get() {
        return field
    }
    set(value) {
        field = value
        notifyPropertyChanged(BR.withdraw)
    }

    var withdraw_krw = 0
    @Bindable get() {
        return field
    }
    set(value) {
        field = value
        notifyPropertyChanged(BR.withdraw_krw)
    }

    var principal = 0
    @Bindable get() {
        return field
    }
    set(value) {
        field = value
        notifyPropertyChanged(BR.principal)
    }

    var indexOfCurrency = 0
    @Bindable get() {
        return field
    }
    set(value) {
        field = value
        notifyPropertyChanged(BR.indexOfCurrency)
    }

    var deposit_rate = 0
    @Bindable get() {
        return field
    }
    set(value) {
        field = value
        notifyPropertyChanged(BR.deposit_rate)
    }

    var withdraw_rate = 0
    @Bindable get() {
        return field
    }
    set(value) {
        field = value
        notifyPropertyChanged(BR.withdraw_rate)
    }

    var evaluation_rate = 0
    @Bindable get() {
        return field
    }
    set(value) {
        field = value
        notifyPropertyChanged(BR.evaluation_rate)
    }

    var evaluation_krw = 0
    @Bindable get() {
        return field
    }
    set(value) {
        field = value
        notifyPropertyChanged(BR.evaluation_krw)
    }
}