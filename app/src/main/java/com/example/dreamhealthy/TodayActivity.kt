package com.example.dreamhealthy

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import java.util.ArrayList
import java.util.Scanner

class TodayActivity : AppCompatActivity() {

    //CAMBIARE CON I VALORI SMARTWATCH
    var hoursSleep:Int = 8
    var minutesSleep:Int = 30
    var minutesRest:Int = 1
    var hoursRest:Int = 0
    var heartbeatsSleep:Float = 45.00f
    var heartbeatsRest:Float = 86.00f
    var reawakenings:Int = 1
    var totalHours:Int = hoursSleep + hoursRest
    var totalMinutes:Int = minutesSleep + minutesRest

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_today)

        // button menu --> from today to menu
        val buttonMenu = findViewById<Button>(R.id.hamburger_button)
        buttonMenu.setOnClickListener {
            val pageMenu = Intent (this, MenuActivity::class.java)
            startActivity(pageMenu)

        }
        //call fun
        updateVar()


        //pie chart
        val pieChart = findViewById<PieChart>(R.id.total_chart)


        pieChart.holeRadius = 70f
        pieChart.transparentCircleRadius = 45f
        pieChart.setHoleColor(Color.TRANSPARENT)

        //description component disabled
        pieChart.description.isEnabled = false
        pieChart.legend.isEnabled = false
        pieChart.setDrawEntryLabels(false)


        val pieList = mutableListOf<PieEntry>()

        //sleep values --> change with correct percentage
        pieList.add(PieEntry(80.00f, "Good Sleep"))
        pieList.add(PieEntry(20.00f,""))

        //value for text Center
        val goodSleep = pieList.find {it.label == "Good Sleep"}?.value ?: 0f
        // text center
        pieChart.setCenterText("$goodSleep%")
        pieChart.setCenterTextSize(20f)
        pieChart.setCenterTextColor(Color.WHITE)

        val colors = listOf(
            ContextCompat.getColor(this, R.color.green_light),
            ContextCompat.getColor(this, R.color.blue_dark)
        )

        //pie chart color
        val pieDataSet = PieDataSet(pieList, "")
        pieDataSet.colors = colors

        //allocation of data
        val pieData = PieData(pieDataSet)
        pieChart.data = pieData

        //animation
        pieChart.animateY(1000)
        pieChart.invalidate()


    }

    //Fun update var and print
    fun updateVar() {
        //var
        addMinutes()
        addMinutesSleep()
        addMinutesRest()
        //print
        printTotal()
    }



        // days of the week section top


        //minutes and hour (60 minutes = 1 hour)
        //total
        fun addMinutes(){
            if (totalMinutes >= 60){
                totalMinutes = totalMinutes - 60
                totalHours = totalHours + 1
            } else {
                totalMinutes = totalMinutes
            }
        }
        //sleep
        fun addMinutesSleep(){
            if(minutesSleep >= 60){
                minutesSleep = minutesSleep - 60
                hoursSleep = hoursSleep + 1
            } else {
                minutesSleep = minutesSleep
            }
        }
        //rest
        fun addMinutesRest() {
            if (minutesRest >= 60) {
                minutesRest = minutesRest - 60
                hoursRest = hoursRest + 1
            } else {
                minutesRest = minutesRest
            }
        }

        //Print input total hours and minutes

        fun printTotal() {
            val totalHoursTextView = findViewById<TextView>(R.id.total_hours)
            val totalMinutesTextView = findViewById<TextView>(R.id.total_minutes)

            Log.d("TodayActivity", "Total Hours: $totalHours, Total Minutes: $totalMinutes")
            totalHoursTextView.text = "$totalHours h"
            totalMinutesTextView.text = "$totalMinutes m"
        }

}