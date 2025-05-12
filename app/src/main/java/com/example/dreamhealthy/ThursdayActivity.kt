package com.example.dreamhealthy

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry

class ThursdayActivity : AppCompatActivity() {
    var hoursSleep:Int = 0  //7-9 --> 6/10
    var minutesSleep:Int = 0
    var minutesRest:Int = 0
    var hoursRest:Int = 0
    var heartRateAverage:Float = 0.00f //40-50.50 --> 50.51/60
    var awakening:Int = 0 //0 --> 1/3
    var Averagebreath:Int = 0   //12-20 --> 10/24
    var noise:Int = 0  //0
    var totalHours:Int = hoursSleep + hoursRest
    var totalMinutes:Int = minutesSleep + minutesRest
    private var goodChartTotalThursday:Float = 100.00f

    //value for navbar
    private lateinit var buttonToday: ImageButton
    private lateinit var buttonMenu: ImageButton
    private lateinit var textButton: TextView
    private lateinit var tuesdayButton: Button
    private lateinit var mondayButton: Button
    private lateinit var wednesdayButton: Button
    private lateinit var fridayButton: Button
    private lateinit var saturdayButton: Button
    private lateinit var sundayButton: Button


    //value for today color
    private var today = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_thursday)

        //init image buttons
        buttonToday = findViewById(R.id.todayBt)
        buttonMenu = findViewById(R.id.menuBt)
        textButton = findViewById(R.id.today_text_button)
        tuesdayButton = findViewById(R.id.tuesday_button)
        mondayButton = findViewById(R.id.monday_button)
        wednesdayButton = findViewById(R.id.wednesday_button)
        fridayButton = findViewById(R.id.friday_button)
        saturdayButton = findViewById(R.id.saturday_button)
        sundayButton = findViewById(R.id.sunday_button)


        //change color today for make evident
        val daysLayout = mapOf(
            Calendar.MONDAY to findViewById<LinearLayout>(R.id.monday_layout),
            Calendar.TUESDAY to findViewById<LinearLayout>(R.id.tuesday_layout),
            Calendar.WEDNESDAY to findViewById<LinearLayout>(R.id.wednesday_layout),
            Calendar.THURSDAY to findViewById<LinearLayout>(R.id.thursday_layout),
            Calendar.FRIDAY to findViewById<LinearLayout>(R.id.friday_layout),
            Calendar.SATURDAY to findViewById<LinearLayout>(R.id.saturday_layout),
            Calendar.SUNDAY to findViewById<LinearLayout>(R.id.sunday_layout)
        )
        daysLayout[today]?.setBackgroundColor(ContextCompat.getColor(this, R.color.blue_light))

        //cal fun
        updateVar()
    }
    //Fun update var and print
    fun updateVar() {
        //general fun
        buttonChange()
        addMinutes()
        addMinutesSleep()
        addMinutesRest()
        pieChartTotal()

        pieChartMonday()
        pieChartTuesday()
        pieChartWednesday()
        pieChartThursday()
        pieChartFriday()
        pieChartSaturday()
        pieChartSunday()
        //fun specific for print

        printTextButton()
        printTotal()
        printNotSleep()
        printSleep()
        printHeartRate()
        printBreath()
        printNoise()
        printAwakening()
    }


    fun calculatePercentageTotalChart(){
        goodChartTotalThursday = 100.00f

        if (hoursSleep < 5 || hoursSleep > 11){
            goodChartTotalThursday = goodChartTotalThursday - 14.00f
        } else if(hoursSleep < 7 || hoursSleep > 9){
            goodChartTotalThursday = goodChartTotalThursday - 5.00f
        }
        if (heartRateAverage > 65.00f){
            goodChartTotalThursday = goodChartTotalThursday - 20.00f
        } else if (heartRateAverage < 40.00f || heartRateAverage > 50.50f){
            goodChartTotalThursday = goodChartTotalThursday - 12.00f
        }
        if (Averagebreath > 27){
            goodChartTotalThursday = goodChartTotalThursday - 18.00f
        } else if (Averagebreath < 12 || Averagebreath > 20){
            goodChartTotalThursday = goodChartTotalThursday - 10.00f
        }
        if (awakening > 3){
            goodChartTotalThursday = goodChartTotalThursday - 10.00f
        } else if (awakening > 0){
            goodChartTotalThursday = goodChartTotalThursday - 4.00f
        }
        if (noise > 0){
            goodChartTotalThursday = goodChartTotalThursday - 3.00f
        }
        if (hoursSleep == 0 && heartRateAverage == 0.00f && Averagebreath == 0 && awakening == 0 && noise == 0){
            goodChartTotalThursday = 0.00f
        }

        val sharedPreferencesThursday = getSharedPreferences("MyPrefsThursday", Context.MODE_PRIVATE)
        sharedPreferencesThursday.edit()
            .putFloat("goodChartTotalThursday", goodChartTotalThursday)
            .apply()
    }



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


    //fun for changing page (navbar)
    fun buttonChange()
    {
        // button hamburger --> from today to menu
        buttonMenu.setOnClickListener {
            val pageMenu = Intent(this, MenuActivity::class.java)
            startActivity(pageMenu)
        }
        // button chart  --> from today to chartanalisys_today

        mondayButton.setOnClickListener {
            val pageMonday = Intent(this, MondayActivity::class.java)
            startActivity(pageMonday)
        }
        tuesdayButton.setOnClickListener {
            val pageTuesday = Intent(this, TuesdayActivity::class.java)
            startActivity(pageTuesday)
        }
        wednesdayButton.setOnClickListener {
            val pageWednesday = Intent(this, WednesdayActivity::class.java)
            startActivity(pageWednesday)
        }
        fridayButton.setOnClickListener {
            val pageFriday = Intent(this, FridayActivity::class.java)
            startActivity(pageFriday)
        }
        saturdayButton.setOnClickListener {
            val pageSaturday = Intent(this, SaturdayActivity::class.java)
            startActivity(pageSaturday)
        }
        sundayButton.setOnClickListener {
            val pageSunday = Intent(this, SundayActivity::class.java)
            startActivity(pageSunday)
        }
    }

    //text button
    fun printTextButton() {
        textButton.text = "Thursday"

    }

    //ALL PIE CHART FUNCTION
    //coordinare con calendario
    // days of the week section top
    fun pieChartMonday() {
        val pieChart = findViewById<PieChart>(R.id.monday_chart)
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
        //receive monday values
        val sharedPreferencesMonday = getSharedPreferences("MyPrefsMonday", Context.MODE_PRIVATE)
        val goodChartTotalMonday = sharedPreferencesMonday.getFloat("goodChartTotalMonday", 0.00f)

        pieList.add(PieEntry(goodChartTotalMonday, "Good Sleep"))
        pieList.add(PieEntry(100.00f - goodChartTotalMonday, ""))
        // text center
        pieChart.setCenterText("M")
        pieChart.setCenterTextSize(10f)
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
    fun pieChartTuesday() {
        val pieChart = findViewById<PieChart>(R.id.tuesday_chart)
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

        //receive tuesday values
        val sharedPreferencesTuesday = getSharedPreferences("MyPrefsTuesday", Context.MODE_PRIVATE)
        val goodChartTotalTuesday = sharedPreferencesTuesday.getFloat("goodChartTotalTuesday", 0.00f)
        pieList.add(PieEntry(goodChartTotalTuesday, "Good Sleep"))
        pieList.add(PieEntry(100.00f - goodChartTotalTuesday, ""))
        // text center
        pieChart.setCenterText("T")
        pieChart.setCenterTextSize(10f)
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
    fun pieChartWednesday() {
        val pieChart = findViewById<PieChart>(R.id.wednesday_chart)
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

        //receive wednesday values
        val sharedPreferencesWednesday = getSharedPreferences("MyPrefsWednesday", Context.MODE_PRIVATE)
        val goodChartTotalWednesday = sharedPreferencesWednesday.getFloat("goodChartTotalWednesday", 0.00f)
        pieList.add(PieEntry(goodChartTotalWednesday, "Good Sleep"))
        pieList.add(PieEntry(100.00f - goodChartTotalWednesday, ""))
        // text center
        pieChart.setCenterText("W")
        pieChart.setCenterTextSize(10f)
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
    fun pieChartThursday() {
        val pieChart = findViewById<PieChart>(R.id.thursday_chart)
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
        //sleep values
        pieList.add(PieEntry(goodChartTotalThursday, "Good Sleep"))
        pieList.add(PieEntry(100.00f - goodChartTotalThursday, ""))
        // text center
        pieChart.setCenterText("T")
        pieChart.setCenterTextSize(10f)
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
    fun pieChartFriday() {
        val pieChart = findViewById<PieChart>(R.id.friday_chart)
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

        //receive friday values
        val sharedPreferencesFriday = getSharedPreferences("MyPrefsFriday", Context.MODE_PRIVATE)
        val goodChartTotalFriday = sharedPreferencesFriday.getFloat("goodChartTotalFriday", 0.00f)
        pieList.add(PieEntry(goodChartTotalFriday, "Good Sleep"))
        pieList.add(PieEntry(100.00f - goodChartTotalFriday, ""))
        // text center
        pieChart.setCenterText("F")
        pieChart.setCenterTextSize(10f)
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
    fun pieChartSaturday() {
        val pieChart = findViewById<PieChart>(R.id.saturday_chart)
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

        //receive saturday values
        val sharedPreferencesSaturday = getSharedPreferences("MyPrefsSaturday", Context.MODE_PRIVATE)
        val goodChartTotalSaturday = sharedPreferencesSaturday.getFloat("goodChartTotalSaturday", 0.00f)
        pieList.add(PieEntry(goodChartTotalSaturday, "Good Sleep"))
        pieList.add(PieEntry(100.00f - goodChartTotalSaturday, ""))
        // text center
        pieChart.setCenterText("S")
        pieChart.setCenterTextSize(10f)
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
    fun pieChartSunday() {
        val pieChart = findViewById<PieChart>(R.id.sunday_chart)
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

        //receive sunday values
        val sharedPreferencesSunday = getSharedPreferences("MyPrefsSunday", Context.MODE_PRIVATE)
        val goodChartTotalSunday = sharedPreferencesSunday.getFloat("goodChartTotalSunday", 0.00f)
        pieList.add(PieEntry(goodChartTotalSunday, "Good Sleep"))
        pieList.add(PieEntry(100.00f - goodChartTotalSunday, ""))

        // text center
        pieChart.setCenterText("S")
        pieChart.setCenterTextSize(10f)
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


    //Print input total hours and minutes
    fun printTotal() {
        val totalHoursTextView = findViewById<TextView>(R.id.total_hours)
        val totalMinutesTextView = findViewById<TextView>(R.id.total_minutes)

        //Log.d("MondayActivity", "Total Hours: $totalHours, Total Minutes: $totalMinutes")
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

        calculatePercentageTotalChart()

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
        pieList.add(PieEntry(goodChartTotalThursday, "Good Sleep"))
        pieList.add(PieEntry(100.00F - goodChartTotalThursday, ""))

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
}