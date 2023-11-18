package com.v_petr.qrandbarcodescanner.utils

import androidx.databinding.InverseMethod
import java.time.Instant


object Converter {
    @InverseMethod("stringToInt")
    @JvmStatic
    fun intToString(value: Int): String {
        return value.toString()
    }

    @JvmStatic
    fun stringToInt(value: String): Int {
        return try {
            value.toInt()
        } catch (e: NumberFormatException) {
            0
        }
    }

    @JvmStatic
    fun timestampToDate(value: Any): String {
        value.toString().toLong()
        val date = Instant.ofEpochMilli(value.toString().toLong())
//        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.mmm'Z'")
//        return format.format(value.toString().toLong())
        return date.toString()
    }


}