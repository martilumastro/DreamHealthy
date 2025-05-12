package com.example.dreamhealthy

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MyAlarmsClockActivity : AppCompatActivity() {

    private lateinit var buttonWeekValue: ImageButton
    private lateinit var buttonMenu: ImageButton
    private lateinit var buttonMelody: ImageButton
    private lateinit var buttonAlarmMonday: Button

    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_my_alarms_clock)


            //init image buttons
        buttonMenu = findViewById(R.id.menu_bt)
        buttonWeekValue = findViewById(R.id.week_values_bt)
        buttonMelody = findViewById(R.id.melody_bt)
        buttonAlarmMonday = findViewById(R.id.monday_bt)

        //call fun
        buttonChange()
    }

    fun buttonChange() {
        // button hamburger --> from alarms to menu
        buttonMenu.setOnClickListener {
            val pageMenu = Intent(this, MenuActivity::class.java)
            startActivity(pageMenu)
        }
        buttonWeekValue.setOnClickListener {
            val pageWeekValue = Intent (this, WeekValuesMenuActivity::class.java)
            startActivity(pageWeekValue)
        }
        // button melody --> from alarms to melody
        buttonMelody.setOnClickListener {
            val pageMelody = Intent(this, MelodyActivity::class.java)
            startActivity(pageMelody)
        }
        buttonAlarmMonday.setOnClickListener {
            val pageAlarm = Intent(this, MondayAlarmClockActivity::class.java)
            startActivity(pageAlarm)
        }

    }
}
