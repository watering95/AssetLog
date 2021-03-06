package com.example.watering.assetlog.viewmodel

import android.app.Application
import androidx.databinding.Bindable
import androidx.lifecycle.*
import com.example.watering.assetlog.BR
import com.example.watering.assetlog.entities.Spend

class ViewModelEditSpend(application:Application) : ObservableViewModel(application) {
    var oldCode:String = "000000000000"
    var newCode:String = oldCode
    var idAccount:Int? = 0
    var idCard:Int? = 0

    var listOfMain = MutableLiveData<List<String?>>()
    @Bindable get() {
        return field
    }
    set(value) {
        field = value
        notifyPropertyChanged(BR.listOfMain)
    }

    var spend: Spend = Spend()
    @Bindable get() {
        return field
    }
    set(value) {
        field = value

        value.apply {
            newCode = newCode.replaceRange(2,10, date?.removeRange(4,5)?.removeRange(6,7).toString())
        }

        notifyPropertyChanged(BR.spend)
    }

    var indexOfMain: Int = 0
    @Bindable get() {
        return field
    }
    set(value) {
        field = value
        listOfSub = Transformations.switchMap(listOfMain) { listOfMain ->
            Transformations.map(getCatSubsByMain(listOfMain[field])) { listOfSub -> listOfSub.map { it.name } }
        } as MutableLiveData<List<String?>>
        indexOfSub = 0
        notifyPropertyChanged(BR.indexOfMain)
    }

    var listOfSub = MutableLiveData<List<String?>> ()
    @Bindable get() {
        return field
    }
    set(value) {
        field = value
        notifyPropertyChanged(BR.listOfSub)
    }

    var indexOfSub: Int = 0
    @Bindable get() {
        return field
    }
    set(value) {
        field = value
        notifyPropertyChanged(BR.indexOfSub)
    }

    var listOfPay2 = MutableLiveData<List<String?>> ()
    @Bindable get() {
        return field
    }
    set(value) {
        field = value
        notifyPropertyChanged(BR.listOfPay2)
    }

    var indexOfPay1:Int = 0
    @Bindable get() {
        return field
    }
    set(value) {
        field = value
        newCode = newCode.replaceRange(0,1,"${field+1}")
        when(field) {
            0 -> listOfPay2 = Transformations.map(allAccounts) { list -> list.map { it.number }} as MutableLiveData<List<String?>>
            1 -> listOfPay2 = Transformations.map(allCards) { list -> list.map { it.number } } as MutableLiveData<List<String?>>
        }
        indexOfPay2 = 0
        notifyPropertyChanged(BR.indexOfPay1)
    }

    var indexOfPay2:Int = 0
    @Bindable get() {
        return field
    }
    set(value) {
        field = value
        notifyPropertyChanged(BR.indexOfPay2)
    }
}