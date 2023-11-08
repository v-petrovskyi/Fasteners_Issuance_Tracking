package com.v_petr.qrandbarcodescanner.utils

import androidx.databinding.InverseMethod


object Converter {
    @InverseMethod("stringToInt")
    @JvmStatic fun intToString(value: Int): String {
        return value.toString()
    }

    @JvmStatic fun stringToInt(value: String): Int {
        return try {
            value.toInt()
        } catch (e: NumberFormatException) {
            0
        }
    }
}