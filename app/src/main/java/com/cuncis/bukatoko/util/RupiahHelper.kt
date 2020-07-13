package com.cuncis.bukatoko.util

import java.text.NumberFormat
import java.util.*

object RupiahHelper {
    @JvmStatic
    fun rupiah(number: Int): String? {
        val numberFormat: NumberFormat = NumberFormat.getInstance(Locale.GERMANY)
        return numberFormat.format(number)
    }

    @JvmStatic
    fun rupiah(number: Double): String? {
        val numberFormat: NumberFormat = NumberFormat.getInstance(Locale.GERMANY)
        return numberFormat.format(number)
    }
}