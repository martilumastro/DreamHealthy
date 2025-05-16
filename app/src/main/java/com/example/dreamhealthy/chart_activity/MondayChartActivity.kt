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
import com.example.dreamhealthy.MenuActivity
import com.example.dreamhealthy.R
import com.example.dreamhealthy.TimeAxisFormatter
import com.example.dreamhealthy.databinding.ActivityMondayChartBinding
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.LimitLine

class MondayChartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMondayChartBinding

    val heart_rate_values = ArrayList<Entry>()
    val temperature_values = ArrayList<Entry>()
    val noise_values = ArrayList<Entry>()


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMondayChartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        buttonChange()
       setDataChart()
    }

    fun setDataChart()
    {
        HeartValues()
        TemperatureValues()
        NoiseValues()
        setChart()
    }

    private fun HeartValues()
    {
       heart_rate_values.add(Entry(23.0f,70f)) //x hour y 3 values
       heart_rate_values.add(Entry(23.5f,65f))
       heart_rate_values.add(Entry(0.0f,60f))
    }

    private fun TemperatureValues()
    {
      temperature_values.add(Entry(23.0f,36.7f))
      temperature_values.add(Entry(23.5f,36.5f))
      temperature_values.add(Entry(0.0f,36.4f))
    }

    private fun NoiseValues()
    {
        noise_values.add(Entry(23.0f, 35f))
        noise_values.add(Entry(23.5f, 30f))
        noise_values.add(Entry(0.0f, 25f))
    }





        private fun setChart() {
        val xAxis = binding.LineChart.xAxis
        val now = java.util.Calendar.getInstance()
        val current_hour = now.get(java.util.Calendar.HOUR_OF_DAY)
        val current_minute = now.get(java.util.Calendar.MINUTE)
        val current_time_float = current_hour+ (current_minute / 60.0f)

        val HeartRateSet = LineDataSet(heart_rate_values, "Heart Rate")
        HeartRateSet.color = Color.RED
        HeartRateSet.setDrawCircles(false)

        val TemperatureSet = LineDataSet(temperature_values, "Temperature Body")
        TemperatureSet.color = Color.YELLOW
        TemperatureSet.setDrawCircles(false)

        val NoiseSet = LineDataSet(noise_values, "Noise Ambience (DB)")
        NoiseSet.color = Color.GREEN
        NoiseSet.setDrawCircles(false)

        val lineData = LineData(HeartRateSet,TemperatureSet,NoiseSet)
        binding.LineChart.data = lineData
        binding.LineChart.invalidate()

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
       limit_line(current_time_float,xAxis) // faccio la funzione per risparmiare tempo
       anim_line()

    }


                    private fun limit_line(current_time : Float , xAxis :XAxis)
                    {
                    val currentLine = LimitLine(current_time,"Ora Attuale")
                        currentLine.lineWidth = 2f
                        currentLine.lineColor = Color.MAGENTA
                        currentLine.textColor = Color.MAGENTA
                        currentLine.labelPosition = LimitLine.LimitLabelPosition.RIGHT_TOP
                        xAxis.removeAllLimitLines()  //mostriamo solo quella attuale
                        xAxis.addLimitLine(currentLine)
                    }
                     private fun anim_line()
                    {
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
