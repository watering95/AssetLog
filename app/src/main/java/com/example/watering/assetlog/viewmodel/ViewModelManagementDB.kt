package com.example.watering.assetlog.viewmodel

import android.app.Application
import androidx.databinding.Bindable
import com.example.watering.assetlog.BR

class ViewModelManagementDB(application:Application) : ObservableViewModel(application) {
    var listOfFile: List<String> = listOf()
    @Bindable get() {
        return field
    }
    set(value) {
        field = value
        notifyPropertyChanged(BR.listOfFile)
    }
}