package com.example.dreamhealthy

import android.annotation.SuppressLint
import com.github.mikephil.charting.formatter.ValueFormatter
/*Cara Martina ti implemento una classe che puo ritornarci utile quando implementiamo l'orario attuale(timestamp) https://www.baeldung.com/kotlin/current-date-time  */
class TimeAxisFormatter : ValueFormatter() {
    @SuppressLint("SuspiciousIndentation")
    override fun getFormattedValue(value: Float): String {
     val total_minutes = (value * 60).toInt()
     val hours = total_minutes / 60
     val minutes = total_minutes  %60
        return String.format("%02d:%02d",hours,minutes)
    }
}