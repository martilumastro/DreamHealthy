package com.example.dreamhealthy

import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.dreamhealthy.databinding.ActivityCalendarBinding

class CalendarActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCalendarBinding
    private lateinit var buttonWeekValue: ImageButton
    private lateinit var buttonMyAlarmsClock: ImageButton
    private lateinit var buttonToday: ImageButton
    private lateinit var buttonMenu: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCalendarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // init
        buttonWeekValue = findViewById(R.id.week_values_bt)
        buttonMyAlarmsClock = findViewById(R.id.my_alarms_clock_bt)
        buttonMenu = findViewById(R.id.menu_bt)
        buttonToday = findViewById(R.id.today_bt)


        // call fun
        calendarStructure()
        changePage()
    }

    fun changePage(){
        buttonWeekValue.setOnClickListener {
            val pageWeekValue = Intent (this, WeekValuesMenuActivity::class.java)
            startActivity(pageWeekValue)
        }
        buttonMyAlarmsClock.setOnClickListener {
            val intent = Intent(this, MyAlarmsClockActivity::class.java)
            startActivity(intent)
        }
        buttonMenu.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }
        buttonToday.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }

    fun calendarStructure(){
        binding.calendarView.setOnDateChangeListener {_, year, month, day ->
            val date = ("%02d".format(day) + "-" + "%02d".format((month + 1)) + "-" + year)
            binding.textView.text = date
        }
    }
}