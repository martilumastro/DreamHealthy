package com.example.dreamhealthy

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Button


import com.example.dreamhealthy.databinding.ActivityChartBinding //package per il grafico della activity Chart

//varie componenti gentilmente offerti da github
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate

//import android.widget.TextView

class ChartActivity : AppCompatActivity()
{
    val profitValues = ArrayList<BarEntry>() // dichiaro qua
    private lateinit var binding: ActivityChartBinding
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        binding = ActivityChartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        enableEdgeToEdge()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main))
        { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets

        }
           // ritorna indietro al file today

            val buttonToday = findViewById<Button>(R.id.today_button)
            buttonToday.setOnClickListener {
                val pageToday = Intent(this, TodayActivity::class.java)
                startActivity(pageToday)
            }
            dataListing()




    } // end Oncreate
    private fun dataListing()
    {
        profitValues.add(BarEntry(0.toFloat(),13.toFloat())) //y ordinata x ascissa
        profitValues.add(BarEntry(1.toFloat(),27.toFloat()))
        profitValues.add(BarEntry(2.toFloat(),4.toFloat()))
        profitValues.add(BarEntry(3.toFloat(),29.toFloat()))
        profitValues.add(BarEntry(4.toFloat(),41.toFloat()))

        setChart()

    }

    private fun setChart()
    {
        binding.chart.description.isEnabled = false
        binding.chart.setMaxVisibleValueCount(25)
        binding.chart.setPinchZoom(false)
        binding.chart.setDrawBarShadow(false)
        binding.chart.setDrawGridBackground(false)

        val xAxis = binding.chart.xAxis

        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(false)
        xAxis.granularity = 1f
        xAxis.isGranularityEnabled = true
        xAxis.valueFormatter = IndexAxisValueFormatter(arrayListOf("Sales","Profit"))


        binding.chart.axisLeft.setDrawGridLines(false)
        binding.chart.legend.isEnabled = false

        val barDataSetter : BarDataSet

        if(binding.chart.data != null && binding.chart.data.dataSetCount > 0) // quando è pieno
        {
            barDataSetter =  binding.chart.data.getDataSetByIndex(0) as BarDataSet
            barDataSetter.values = profitValues
            binding.chart.data.notifyDataChanged()
            binding.chart.notifyDataSetChanged()
        } else {
            barDataSetter = BarDataSet(profitValues,"Data Set")
            barDataSetter.colors = ColorTemplate.VORDIPLOM_COLORS.toList()
            barDataSetter.setDrawValues(false)


            val dataSet = ArrayList<IBarDataSet>()
            dataSet.add(barDataSetter)

            val data = BarData(dataSet as List<IBarDataSet>)
            binding.chart.data = data
            binding.chart.setFitBars(true)
        }
        binding.chart.invalidate()

    } // end setChart()

}//end class