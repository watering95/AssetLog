package com.example.watering.assetlog.model

import androidx.databinding.InverseMethod
import java.text.DecimalFormat

object Converter {
    private val df_int = DecimalFormat("#,###")
    private val df_double = DecimalFormat("#,##0.##")

    @InverseMethod("strToInt")
    @JvmStatic fun intToStr(value:Int): String = df_int.format(value)

    @JvmStatic fun strToInt(value:String): Int = df_int.parse(value).toInt()

    @InverseMethod("strToDouble")
//    @JvmStatic fun doubleToStr(value:Double): String = String.format("%.2f",value)
    @JvmStatic fun doubleToStr(value:Double): String = df_double.format(value)

//    @JvmStatic fun strToDouble(value:String): Double = value.toDouble()
    @JvmStatic fun strToDouble(value:String): Double = df_double.parse(value).toDouble()
}