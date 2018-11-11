package com.example.watering.assetlog.viewmodel

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.example.watering.assetlog.entities.Card

class EditCardViewModel(@Bindable var card: Card?, @Bindable var selected:Int?) : BaseObservable()