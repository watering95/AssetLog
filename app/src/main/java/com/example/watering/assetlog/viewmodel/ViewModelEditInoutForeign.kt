package com.example.watering.assetlog.viewmodel

import android.app.Application
import androidx.databinding.Bindable
import com.example.watering.assetlog.BR
import com.example.watering.assetlog.entities.IOForeign

class ViewModelEditInoutForeign(application:Application) : ObservableViewModel(application) {
    var io: IOForeign = IOForeign()

    var date:String? = ""
    @Bindable get() {
        return field
    }
    set(value) {
        field = value
        notifyPropertyChanged(BR.date)
    }

    var deposit: Double? = 0.0
    @Bindable get() {
        return field
    }
    set(value) {
        field = value
        io.input = value
        if(deposit != 0.0) deposit_rate = deposit_krw?.div(deposit!!)
        notifyPropertyChanged(BR.deposit_rate)
        notifyPropertyChanged(BR.deposit)
    }

    var deposit_krw: Int? = 0
    @Bindable get() {
        return field
    }
    set(value) {
        field = value
        io.input_krw = value
        if(deposit != 0.0) deposit_rate = deposit_krw?.div(deposit!!)
        notifyPropertyChanged(BR.deposit_rate)
        notifyPropertyChanged(BR.deposit_krw)
    }

    var withdraw: Double? = 0.0
    @Bindable get() {
        return field
    }
    set(value) {
        field = value
        io.output = value
        if(withdraw != 0.0) withdraw_rate = withdraw_krw?.div(withdraw!!)
        notifyPropertyChanged(BR.withdraw_rate)
        notifyPropertyChanged(BR.withdraw)
    }

    var withdraw_krw: Int? = 0
    @Bindable get() {
        return field
    }
    set(value) {
        field = value
        io.output_krw = value
        if(withdraw != 0.0) withdraw_rate = withdraw_krw?.div(withdraw!!)
        notifyPropertyChanged(BR.withdraw_rate)
        notifyPropertyChanged(BR.withdraw_krw)
    }

    var principal: Double? = 0.0
    @Bindable get() {
        return field
    }
    set(value) {
        field = value
        notifyPropertyChanged(BR.principal)
    }

    var indexOfCurrency: Int? = 0
    @Bindable get() {
        return field
    }
    set(value) {
        field = value
        io.currency = value
        notifyPropertyChanged(BR.indexOfCurrency)
    }

    var deposit_rate: Double? = 0.0
    @Bindable get() {
        return field
    }
    set(value) {
        field = value
        notifyPropertyChanged(BR.deposit_rate)
    }

    var withdraw_rate: Double? = 0.0
    @Bindable get() {
        return field
    }
    set(value) {
        field = value
        notifyPropertyChanged(BR.withdraw_rate)
    }

    var evaluation_rate: Double? = 0.0
    @Bindable get() {
        return field
    }
    set(value) {
        field = value
        notifyPropertyChanged(BR.evaluation_rate)
    }

    var evaluation_krw: Double? = 0.0
    @Bindable get() {
        return field
    }
    set(value) {
        field = value
        io.evaluation_krw = value
        if(principal != 0.0) evaluation_rate = evaluation_krw?.div(principal!!)
        notifyPropertyChanged(BR.evaluation_rate)
        notifyPropertyChanged(BR.evaluation_krw)
    }
}