package com.example.watering.assetlog.model

import androidx.databinding.InverseMethod

object Converter {
    @InverseMethod("strToInt")
    @JvmStatic fun intToStr(value:Int): String = String.format("%d",value)

    @JvmStatic fun strToInt(value:String): Int = value.toInt()

    @InverseMethod("strToDouble")
    @JvmStatic fun doubleToStr(value:Double): String = String.format("%.2f",value)

    @JvmStatic fun strToDouble(value:String): Double = value.toDouble()
}