package com.example.watering.assetlog.viewmodel

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.example.watering.assetlog.entities.CategorySub

class EditCategorySubViewModel(@Bindable var categorySub: CategorySub?, @Bindable var selected:Int?) : BaseObservable()