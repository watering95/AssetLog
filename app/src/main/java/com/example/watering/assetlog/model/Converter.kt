package com.example.watering.assetlog.model

import androidx.databinding.InverseMethod

object Converter {
    @InverseMethod("strToInt")
    @JvmStatic fun intToStr(value:Int): String = String.format("%d",value)

    @JvmStatic fun strToInt(value:String): Int = value.toInt()
}