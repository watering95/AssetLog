package com.example.watering.assetlog.viewmodel

import android.app.Application
import androidx.databinding.Bindable
import androidx.lifecycle.*
import com.example.watering.assetlog.BR
import com.example.watering.assetlog.entities.Spend

class ViewModelEditSpend(application:Application) : ObservableViewModel(application) {
    @Bindable var listOfMain:LiveData<List<String?>> = Transformations.map(getCatMainsByKind("spend")) { list -> list.map { it.name }}
    @Bindable var listOfPay:LiveData<List<String?>>? = null
    @Bindable var indexOfPay1:Int = 0
    @Bindable var indexOfPay2:Int = 0

    var spend: Spend? = null
    @Bindable get() {
        return field
    }
    set(value) {
        field = value
        notifyPropertyChanged(BR.spend)
    }

    var indexOfMain: Int = 0
    @Bindable get() {
        return field
    }
    set(value) {
        field = value
        listOfSub = Transformations.switchMap(listOfMain) { listOfMain ->
            Transformations.map(getCatSubsByMain(listOfMain[value])) { listOfSub -> listOfSub.map { it.name } }
        }
        notifyPropertyChanged(BR.indexOfMain)
    }

    var listOfSub: LiveData<List<String?>>? = null
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
    }
}