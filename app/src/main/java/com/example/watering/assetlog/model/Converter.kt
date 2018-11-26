package com.example.watering.assetlog.model

import androidx.databinding.InverseMethod

object Converter {
    @InverseMethod("strToInt")
    @JvmStatic fun intToStr(value:Int): String {

        return String.format("%d",value)
    }

    @JvmStatic fun strToInt(value:String): Int {

        return value.toInt()
    }
}