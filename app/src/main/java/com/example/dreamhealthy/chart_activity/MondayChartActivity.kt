package com.example.dreamhealthy.chart_activity

import com.example.dreamhealthy.week_activity.MondayActivity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageButton
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import android.graphics.Color
import android.util.Log
import com.example.dreamhealthy.MenuActivity
import com.example.dreamhealthy.R
import com.example.dreamhealthy.TimeAxisFormatter
import com.example.dreamhealthy.databinding.ActivityMondayChartBinding
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.LimitLine
import org.json.JSONObject
import org.json.JSONArray
import org.json.JSONException
import java.util.Locale

class MondayChartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMondayChartBinding

    val heart_rate_values = mutableListOf<Entry>()
    val temperature_values = mutableListOf<Entry>()
    val noise_values = mutableListOf<Entry>()

    val calendar = java.util.Calendar.getInstance()
    val dayOfweek = calendar.get(java.util.Calendar.DAY_OF_WEEK)

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMondayChartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        buttonChange()
        setDataChart()
    }

    fun setDataChart() {
        loadSimulateData()
        setChart()
    }

    @SuppressLint("SuspiciousIndentation")
    private fun loadSimulateData() {
        val today = java.text.SimpleDateFormat("EEEE", Locale.getDefault()).format(java.util.Date())
        val sharedPrefs = getSharedPreferences("sim_data", MODE_PRIVATE)
        val jsonString = sharedPrefs.getString("sim_data_list", null)

        if (jsonString != null) {
            val jsonArray = JSONArray(jsonString)
            for (i in 0 until jsonArray.length()) {
                try {
                    val obj = jsonArray.getJSONObject(i)
                    if (obj.getString("day") == today) {
                        heartVal(obj)
                        tempVal(obj)
                        noiseLev(obj)
                    }
                } catch (e: JSONException) {
                    Log.e("JSON Parsing", "ERROR IN THE READ OF OBJECT JASON :${e.message}")
                }
            }
        }
    }

    private fun heartVal(obj: JSONObject) {
        val hr = obj.getDouble("heart").toFloat()
        val time = obj.getDouble("time").toFloat()
        heart_rate_values.add(Entry(time, hr))
    }

    private fun tempVal(obj: JSONObject) {
        val tp = obj.getDouble("temp").toFloat()
        val time = obj.getDouble("time").toFloat()
        temperature_values.add(Entry(time, tp))
    }


    private fun noiseLev(obj: JSONObject) {
        val ns = obj.getDouble("noise").toFloat()
        val time = obj.getDouble("time").toFloat()
        noise_values.add(Entry(time, ns))
    }

    private fun setChart() {
        val xAxis = binding.LineChart.xAxis
        val now = java.util.Calendar.getInstance()
        val current_hour = now.get(java.util.Calendar.HOUR_OF_DAY)
        val current_minute = now.get(java.util.Calendar.MINUTE)
        val current_time_float = current_hour + (current_minute / 60.0f)

        val heartRateSet = LineDataSet(heart_rate_values, "Heart Rate").apply {
            color = Color.RED
            valueTextColor = Color.BLACK
            lineWidth = 2f
            setCircleColor(Color.RED)
            circleRadius = 4f
            setDrawCircles(false)
        }
        val temperatureSet = LineDataSet(temperature_values, "Temperature Body").apply {
            color = Color.BLUE
            valueTextColor = Color.BLACK
            lineWidth = 2f
            setCircleColor(Color.BLUE)
            circleRadius = 4f
            setDrawCircles(false)
        }
        val noiseSet = LineDataSet(noise_values, "Noise Ambience (DB)").apply {
            color = Color.GREEN
            valueTextColor = Color.BLACK
            lineWidth = 2f
            setCircleColor(Color.GREEN)
            circleRadius = 4f
            setDrawCircles(false)
        }


        val lineData = LineData(heartRateSet, temperatureSet, noiseSet)
        binding.LineChart.data = lineData
        binding.LineChart.apply {
            description.text = "Simulated Data - Monday"
            animateX(1000)

        }

        // set x with formatter time
        xAxis.valueFormatter = TimeAxisFormatter()
        xAxis.granularity = 1f
        xAxis.labelRotationAngle = -45f
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(true)
        xAxis.axisMinimum = 0f //00.00
        xAxis.axisMaximum = 24f //24.00
        xAxis.textColor = Color.WHITE

        //set y
        binding.LineChart.axisLeft.textColor = Color.WHITE
        binding.LineChart.axisRight.isEnabled = false

        //Description
        binding.LineChart.description.isEnabled = false
        //create an other value
        val legend = binding.LineChart.legend

        legend.isEnabled = true
        legend.textColor = Color.WHITE
        legend.form = Legend.LegendForm.LINE


        if (dayOfweek == java.util.Calendar.MONDAY) // always check the day
        {
            limit_line(current_time_float, xAxis)
            anim_line()
        }

    }


    private fun limit_line(current_time: Float, xAxis: XAxis) {
        val currentLine = LimitLine(current_time, "Ora Attuale")
        currentLine.lineWidth = 2f
        currentLine.lineColor = Color.MAGENTA
        currentLine.textColor = Color.MAGENTA
        currentLine.labelPosition = LimitLine.LimitLabelPosition.RIGHT_TOP
        xAxis.removeAllLimitLines()
        xAxis.addLimitLine(currentLine)
    }

    private fun anim_line() {
        binding.LineChart.setTouchEnabled(true)
        binding.LineChart.setPinchZoom(true)
        binding.LineChart.animateX(1000)
        binding.LineChart.setNoDataText("No Data Avaible")
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
            val pageToday = Intent(this, MondayActivity::class.java)
            startActivity(pageToday)
        }

    }
}
