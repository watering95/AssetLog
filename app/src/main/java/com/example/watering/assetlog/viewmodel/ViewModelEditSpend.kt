package com.example.watering.assetlog.viewmodel

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.example.watering.assetlog.entities.Spend

class ViewModelEditSpend(@Bindable var spend: Spend?, @Bindable var idx_cat_main:Int?, @Bindable var idx_cat_sub:Int?, @Bindable var idx_approval1:Int?, @Bindable var idx_approval2:Int?) : BaseObservable()