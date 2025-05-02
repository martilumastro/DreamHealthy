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
import com.github.mikephil.charting.charts.Chart
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
    var minutesRest:Int = 0
    var hoursRest:Int = 1
    var heartRateAverage:Float = 45.00f
    var awakening:Int = 1
    var Averagebreath:Int = 15
    var noise:Int = 0
    var totalHours:Int = hoursSleep + hoursRest
    var totalMinutes:Int = minutesSleep + minutesRest

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?)
    {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_today)

        // button chart --> from today to chart
        val buttonChart = findViewById<Button>(R.id.chart_button)
        buttonChart.setOnClickListener {
            val pageChart = Intent(this, ChartActivity::class.java)
            startActivity(pageChart)
        }



        // button menu --> from today to menu
        val buttonMenu = findViewById<Button>(R.id.hamburger_button)
        buttonMenu.setOnClickListener {
            val pageMenu = Intent (this, MenuActivity::class.java)
            startActivity(pageMenu)

        }




        //call fun for update var, val and print
        updateVar()


    }

    //Fun update var and print
    fun updateVar() {
        //general fun
        addMinutes()
        addMinutesSleep()
        addMinutesRest()
        pieChartTotal()
        pieCharFistNrem()
        pieChartSecondNrem()
        pieChartRem()
        //fun specific for print
        printTotal()
        printNotSleep()
        printSleep()
        printHeartRate()
        printBreath()
        printNoise()
        printAwakening()
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

            //Log.d("TodayActivity", "Total Hours: $totalHours, Total Minutes: $totalMinutes")
            totalHoursTextView.text = "$totalHours h"
            totalMinutesTextView.text = "$totalMinutes m"
        }
        //Print input not sleep hours and minutes
        fun printNotSleep() {
            val notSleepHoursTextView = findViewById<TextView>(R.id.not_sleep_hours)
            val notSleepMinutesTextView = findViewById<TextView>(R.id.not_sleep_minutes)

            notSleepHoursTextView.text = "$hoursRest h"
            notSleepMinutesTextView.text = "$minutesRest m"
        }
        //Print input slepp hours and minutes
        fun printSleep(){
            val sleepHoursTextView = findViewById<TextView>(R.id.sleep_hours)
            val sleepMinutesTextView = findViewById<TextView>(R.id.sleep_minutes)

            sleepHoursTextView.text = "$hoursSleep h"
            sleepMinutesTextView.text = "$minutesSleep m"
        }
        //Print input average heart rate
        fun printHeartRate(){
            val heartRateTextView = findViewById<TextView>(R.id.text_heart)
            heartRateTextView.text = "$heartRateAverage bpm"
        }
        //Print input average breath per minutes
        fun printBreath(){
            val breathTextView = findViewById<TextView>(R.id.text_breath)
            breathTextView.text = "$Averagebreath per minute"
        }
        //Print input number noise
        fun printNoise(){
            val noiseTextView = findViewById<TextView>(R.id.text_noise)
            noiseTextView.text = "$noise"
        }
        //Print input number awakening
        fun printAwakening(){
            val awakeningTextView = findViewById<TextView>(R.id.text_awakening)
            awakeningTextView.text = "$awakening"
        }

    //pie chart
    fun pieChartTotal() {
        val pieChart = findViewById<PieChart>(R.id.total_chart)
        //pie chart hole
        pieChart.holeRadius = 80f
        pieChart.transparentCircleRadius = 0f
        pieChart.setHoleColor(Color.TRANSPARENT)
        //description component disabled
        pieChart.description.isEnabled = false
        pieChart.legend.isEnabled = false
        pieChart.setDrawEntryLabels(false)

        //pie chart value val
        val pieList = mutableListOf<PieEntry>()
        //sleep values --> change with correct percentage
        pieList.add(PieEntry(80.00f, "Good Sleep"))
        pieList.add(PieEntry(20.00f, ""))

        //value for text Center
        val goodSleep = pieList.find { it.label == "Good Sleep" }?.value ?: 0f
        // text center
        pieChart.setCenterText("$goodSleep%")
        pieChart.setCenterTextSize(20f)
        pieChart.setCenterTextColor(Color.WHITE)

        //val colors for pie chart
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

    fun pieCharFistNrem(){
        val pieChart = findViewById<PieChart>(R.id.first_nrem_chart)
        //hole
        pieChart.holeRadius = 70f
        pieChart.transparentCircleRadius = 0f
        pieChart.setHoleColor(Color.TRANSPARENT)
        //description component disabled
        pieChart.description.isEnabled = false
        pieChart.legend.isEnabled = false
        pieChart.setDrawEntryLabels(false)

        //value
        val pieList = mutableListOf<PieEntry>()
        pieList.add(PieEntry(45.00f, "first NREM"))
        pieList.add(PieEntry(55.00f, ""))

        val nRem = pieList.find { it.label == "first NREM" }?.value ?: 0f
        // text center
        pieChart.setCenterText("$nRem%")
        pieChart.setCenterTextSize(14f)
        pieChart.setCenterTextColor(Color.WHITE)

        val colors = listOf(
            ContextCompat.getColor(this, R.color.magenta),
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

    fun pieChartSecondNrem(){
        val pieChart = findViewById<PieChart>(R.id.second_nrem_chart)
        //hole
        pieChart.holeRadius = 70f
        pieChart.transparentCircleRadius = 0f
        pieChart.setHoleColor(Color.TRANSPARENT)
        //description component disabled
        pieChart.description.isEnabled = false
        pieChart.legend.isEnabled = false
        pieChart.setDrawEntryLabels(false)
        //value
        val pieList = mutableListOf<PieEntry>()
        pieList.add(PieEntry(25.00f, "second NREM"))
        pieList.add(PieEntry(65.00f, ""))

        val nRem = pieList.find { it.label == "second NREM" }?.value ?: 0f
        pieChart.setCenterText("$nRem%")
        pieChart.setCenterTextSize(14f)
        pieChart.setCenterTextColor(Color.WHITE)

        val colors = listOf(
            ContextCompat.getColor(this, R.color.red_light),
            ContextCompat.getColor(this, R.color.blue_dark)
        )
        val pieDataSet = PieDataSet(pieList, "")
        pieDataSet.colors = colors
        //allocation of data
        val pieData = PieData(pieDataSet)
        pieChart.data = pieData
        //animation
        pieChart.animateY(1000)
        pieChart.invalidate()
    }

    fun pieChartRem(){
        val pieChart = findViewById<PieChart>(R.id.rem_chart)
        //hole caracters
        pieChart.holeRadius = 70f
        pieChart.transparentCircleRadius = 0f
        pieChart.setHoleColor(Color.TRANSPARENT)
        //description component disabled
        pieChart.description.isEnabled = false
        pieChart.legend.isEnabled = false
        pieChart.setDrawEntryLabels(false)
        //value
        val pieList = mutableListOf<PieEntry>()
        pieList.add(PieEntry(30.00f, "REM"))
        pieList.add(PieEntry(70.00f, ""))

        val nRem = pieList.find { it.label == "REM" }?.value ?: 0f
        pieChart.setCenterText("$nRem%")
        pieChart.setCenterTextSize(14f)
        pieChart.setCenterTextColor(Color.WHITE)

        val colors = listOf(
            ContextCompat.getColor(this, R.color.orange),
            ContextCompat.getColor(this, R.color.blue_dark)
        )
        val pieDataSet = PieDataSet(pieList, "")
        pieDataSet.colors = colors
        //allocation of data
        val pieData = PieData(pieDataSet)
        pieChart.data = pieData
        //animation
        pieChart.animateY(1000)
        pieChart.invalidate()
    }
}