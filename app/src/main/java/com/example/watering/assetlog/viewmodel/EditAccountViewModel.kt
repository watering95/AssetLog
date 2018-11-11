package com.example.watering.assetlog.viewmodel

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.example.watering.assetlog.entities.Account

class EditAccountViewModel(@Bindable var account:Account?, @Bindable var selectedGroup:Int?) : BaseObservable()