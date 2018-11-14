package com.example.watering.assetlog.viewmodel

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.example.watering.assetlog.entities.Account

class ViewModelEditAccount(@Bindable var account:Account?, @Bindable var selected:Int?) : BaseObservable()