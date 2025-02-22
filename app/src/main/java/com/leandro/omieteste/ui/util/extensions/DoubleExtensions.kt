package com.leandro.omieteste.ui.util.extensions

import java.text.NumberFormat
import java.util.Locale


fun Double?.formatarMoeda(): String {
    try {
        val brazilianLocal = Locale("pt", "BR")
        val currencyFormatter = NumberFormat.getCurrencyInstance(brazilianLocal)
        return currencyFormatter.format(this)
    } catch (ex : Exception) {return "R$ 0,00"}
}

