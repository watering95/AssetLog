package com.example.watering.assetlog.viewmodel

import android.app.Application
import androidx.databinding.Bindable
import androidx.lifecycle.*
import com.example.watering.assetlog.BR
import com.example.watering.assetlog.entities.Spend

class ViewModelEditSpend(application:Application) : ObservableViewModel(application) {
    @Bindable var listOfMain:LiveData<List<String?>> = Transformations.map(getCatMainsByKind("spend")) { list -> list.map { it.name }}
    var id_sub:Int = 0
    var code:String = ""

    var spend: Spend? = null
    @Bindable get() {
        return field
    }
    set(value) {
        field = value

        value?.apply {
            id_sub = category!!
            this@ViewModelEditSpend.code = this.code!!
        }

        notifyPropertyChanged(BR.spend)
    }

    var indexOfMain: Int = 0
    @Bindable get() {
        return field
    }
    set(value) {
        field = value
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