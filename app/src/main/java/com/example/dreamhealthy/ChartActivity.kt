package com.example.dreamhealthy

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import android.widget.ImageButton

class ChartActivity : AppCompatActivity() {
    private lateinit var todayBtn: ImageButton
    private lateinit var menuBtn: ImageButton
    private lateinit var pieChart: PieChart

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chart)
        todayBtn = findViewById(R.id.todayBt)
        menuBtn = findViewById(R.id.menuBt)

        buttonChange()


        GraphicChart()
        SetDataCharts()
    }



    private fun GraphicChart() {
        pieChart = findViewById(R.id.pie_chart)
        pieChart.holeRadius = 0f
        pieChart.transparentCircleRadius = 0f
        pieChart.description.isEnabled = false
        pieChart.setDrawEntryLabels(false)
        pieChart.setDrawSlicesUnderHole(true)

    }



    private fun SetDataCharts() {
        val pieList = DataChart()
        val colors = ColorsChart()
        val pieDataSet = PieDataSet(pieList, "")
        pieDataSet.colors = colors
        Set_Graphic_Chart(pieDataSet)
        val pieData = PieData(pieDataSet)
        pieData.setValueTextColor(android.R.color.transparent)
        val legend = pieChart.legend
        legend.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        legend.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        legend.orientation = Legend.LegendOrientation.VERTICAL
        legend.setDrawInside(false)
        legend.isEnabled = false
        pieChart.data = pieData
        pieChart.invalidate()
        pieChart.animateY(1000, Easing.EaseInQuart)
    }

    private fun DataChart(): MutableList<PieEntry> {
        return mutableListOf(
            PieEntry(10.0f),
            PieEntry(12.7f),
            PieEntry(3.9f),
            PieEntry(2.1f),
            PieEntry(17.0f),
            PieEntry(21.0f)
        )
    }

    private fun ColorsChart(): MutableList<Int>
    {
        return mutableListOf(
            resources.getColor(R.color.trans_gray_light),
            resources.getColor(R.color.trans_magenta),
            resources.getColor(R.color.trans_blue_dark),
            resources.getColor(R.color.trans_orange),
            resources.getColor(R.color.trans_white),
            resources.getColor(R.color.trans_blue_light)
        )
    }

    private fun Set_Graphic_Chart(dataSet: PieDataSet) {
        val sliceSpaceInDp = 0.5f
        val density = resources.displayMetrics.density
        val sliceSpaceInPx = sliceSpaceInDp * density
        dataSet.sliceSpace = sliceSpaceInPx
    }

    //fun for changing page (navbar)
    fun buttonChange() {
        // button hamburger --> from today to menu
        val buttonMenu = findViewById<ImageButton>(R.id.menuBt)
        buttonMenu.setOnClickListener {
            val pageMenu = Intent(this, MenuActivity::class.java)
            startActivity(pageMenu)
        }
        // button chart  --> from today to chartanalisys_today
        val buttonToday = findViewById<ImageButton>(R.id.todayBt)
        buttonToday.setOnClickListener {
            val pageToday = Intent(this, TodayActivity::class.java)
            startActivity(pageToday)
        }
    }
}