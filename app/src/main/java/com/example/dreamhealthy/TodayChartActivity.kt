package com.example.dreamhealthy

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.dreamhealthy.chart_activity.FridayChartActivity
import com.example.dreamhealthy.chart_activity.MondayChartActivity
import com.example.dreamhealthy.chart_activity.SaturdayChartActivity
import com.example.dreamhealthy.chart_activity.SundayChartActivity
import com.example.dreamhealthy.chart_activity.ThursdayChartActivity
import com.example.dreamhealthy.chart_activity.TuesdayChartActivity
import com.example.dreamhealthy.chart_activity.WednesdayChartActivity
import com.example.dreamhealthy.week_activity.FridayActivity
import com.example.dreamhealthy.week_activity.MondayActivity
import com.example.dreamhealthy.week_activity.SaturdayActivity
import com.example.dreamhealthy.week_activity.SundayActivity
import com.example.dreamhealthy.week_activity.ThursdayActivity
import com.example.dreamhealthy.week_activity.TuesdayActivity
import com.example.dreamhealthy.week_activity.WednesdayActivity
import java.time.DayOfWeek
import java.time.LocalDate

class TodayChartActivity : AppCompatActivity() {
    private val today = LocalDate.now()
    private val dayOfWeek = today.dayOfWeek

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_today_chart)

        // go every time in today page
        when (dayOfWeek) {
            DayOfWeek.MONDAY -> startActivity(Intent(this, MondayChartActivity::class.java))
            DayOfWeek.TUESDAY -> startActivity(Intent(this, TuesdayChartActivity::class.java))
            DayOfWeek.WEDNESDAY -> startActivity(Intent(this, WednesdayChartActivity::class.java))
            DayOfWeek.THURSDAY -> startActivity(Intent(this, ThursdayChartActivity::class.java))
            DayOfWeek.FRIDAY -> startActivity(Intent(this, FridayChartActivity::class.java))
            DayOfWeek.SATURDAY -> startActivity(Intent(this, SaturdayChartActivity::class.java))
            DayOfWeek.SUNDAY -> startActivity(Intent(this, SundayChartActivity::class.java))
        }

        finish()
    }
}