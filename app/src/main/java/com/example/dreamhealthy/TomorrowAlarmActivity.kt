package com.example.dreamhealthy

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import java.time.DayOfWeek
import java.time.LocalDate
@RequiresApi(Build.VERSION_CODES.O)
class TomorrowAlarmActivity : AppCompatActivity(){
    private val tomorrow = LocalDate.now().plusDays(1)
    private val dayOfWeek = tomorrow.dayOfWeek

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_tomorrow_alarm)

        when (dayOfWeek) {
            DayOfWeek.MONDAY -> startActivity(Intent(this, MondayAlarmClockActivity::class.java))
            DayOfWeek.TUESDAY -> startActivity(Intent(this, TuesdayAlarmClockActivity::class.java))
            DayOfWeek.WEDNESDAY -> startActivity(Intent(this, WednesdayAlarmClockActivity::class.java))
            DayOfWeek.THURSDAY -> startActivity(Intent(this, ThursdayAlarmClockActivity::class.java))
            DayOfWeek.FRIDAY -> startActivity(Intent(this, FridayAlarmClockActivity::class.java))
            DayOfWeek.SATURDAY -> startActivity(Intent(this, SaturdayAlarmClockActivity::class.java))
            DayOfWeek.SUNDAY -> startActivity(Intent(this, SundayAlarmClockActivity::class.java))
        }

        finish()
    }
}