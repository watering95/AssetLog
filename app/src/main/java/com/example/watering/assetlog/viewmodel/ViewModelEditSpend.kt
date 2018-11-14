package com.example.watering.assetlog.viewmodel

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.example.watering.assetlog.entities.Spend

class ViewModelEditSpend(@Bindable var spend: Spend?, @Bindable var selected:Int?) : BaseObservable()