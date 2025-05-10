package com.example.dreamhealthy

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MenuActivity : AppCompatActivity() {

    private lateinit var buttonHome: ImageButton
    private lateinit var buttonToday: ImageButton
    private lateinit var textButton: TextView
    private lateinit var buttonAlarms: Button
    private lateinit var buttonCalendar: Button
    private lateinit var buttonUsefulAdvince: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        //init image buttons
        buttonAlarms = findViewById(R.id.alarm_clock_bt)
        textButton = findViewById(R.id.menu_text_button)
        buttonToday = findViewById(R.id.todayBt)
        buttonHome = findViewById(R.id.homeBt)
        buttonCalendar = findViewById(R.id.calendar_bt)
        buttonUsefulAdvince = findViewById(R.id.useful_advince_bt)


        //call fun
        buttonChange()
        printTextButton()

    }

    //fun for all changing page
    fun buttonChange(){
        // button home --> from menu to home
        buttonHome.setOnClickListener {
            val pageHome = Intent (this, MainActivity::class.java)
            startActivity(pageHome)
        }
        // button today --> from menu to today
        buttonToday.setOnClickListener {
            val pageToday = Intent (this, TodayActivity::class.java)
            startActivity(pageToday)
        }

        buttonAlarms.setOnClickListener {
            val pageAlarms = Intent (this, MyAlarmsClockActivity::class.java)
            startActivity(pageAlarms)
        }
        buttonCalendar.setOnClickListener {
            val pageCalendar = Intent (this, CalendarActivity::class.java)
            startActivity(pageCalendar)
        }
        buttonUsefulAdvince.setOnClickListener {
            val pageUsefulAdvince = Intent (this, UsefulAdvinceActivity::class.java)
            startActivity(pageUsefulAdvince)
        }
    }

    //text button
    fun printTextButton() {
        textButton.text = "Menu"
    }
}