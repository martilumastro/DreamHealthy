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

class    MenuActivity : AppCompatActivity() {

    private lateinit var buttonToday: ImageButton
    private lateinit var buttonWeekValue: ImageButton
    private lateinit var buttonMenu: ImageButton
    private lateinit var textButton: TextView
    private lateinit var buttonAlarms: Button
    private lateinit var buttonCalendar: Button
    private lateinit var buttonUsefulAdvince: Button


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        //init image buttons
        buttonAlarms = findViewById(R.id.alarm_clock_bt)
        textButton = findViewById(R.id.menu_text_button)
        buttonMenu = findViewById(R.id.menuBt)
        buttonWeekValue = findViewById(R.id.week_values_bt)
        buttonToday = findViewById(R.id.today_bt)
        buttonCalendar = findViewById(R.id.calendar_bt)
        buttonUsefulAdvince = findViewById(R.id.useful_advince_bt)


        //call fun
        buttonChange()
        printTextButton()

    }

    //fun for all changing page
    fun buttonChange(){
        // button home --> from menu to home
        buttonToday.setOnClickListener {
            val pageToday = Intent (this, MainActivity::class.java)
            startActivity(pageToday)
        }
        buttonWeekValue.setOnClickListener {
            val pageWeekValue = Intent (this, WeekValuesMenuActivity::class.java)
            startActivity(pageWeekValue)
        }
        buttonMenu.setOnClickListener {
            val pageMenu = Intent (this, MenuActivity::class.java)
            startActivity(pageMenu)
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