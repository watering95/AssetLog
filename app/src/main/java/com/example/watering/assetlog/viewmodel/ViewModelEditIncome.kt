package com.example.watering.assetlog.viewmodel

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.example.watering.assetlog.entities.Income

class ViewModelEditIncome(@Bindable var income: Income?, @Bindable var selected:Int?) : BaseObservable()