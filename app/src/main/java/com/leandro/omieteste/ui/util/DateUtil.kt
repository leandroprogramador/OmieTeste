package com.leandro.omieteste.ui.util

import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

object DateUtil {
    fun formatarTImeStampParaData(timeStamp : Long) : String {
        val formato = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale("pt", "BR"))
        formato.timeZone = TimeZone.getTimeZone("GMT-3")
        val data = java.util.Date(timeStamp)
        return formato.format(data)
    }
}