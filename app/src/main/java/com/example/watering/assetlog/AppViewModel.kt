package com.example.watering.assetlog

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class AppViewModel(application: Application) : AndroidViewModel(application) {
    var mRepository: AppRepository = AppRepository(application)

}