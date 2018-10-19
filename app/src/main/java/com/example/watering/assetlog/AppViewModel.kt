package com.example.watering.assetlog

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class AppViewModel: AndroidViewModel {
    lateinit var repository: AppRepository

    constructor(application: Application) : super(application) {
        repository = AppRepository(application)
    }
}