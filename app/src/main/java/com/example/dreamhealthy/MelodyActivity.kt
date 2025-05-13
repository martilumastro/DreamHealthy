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

class MelodyActivity : AppCompatActivity() {

    //var
    private lateinit var buttonMenu: ImageButton
    private lateinit var buttonAlarms: ImageButton
    private lateinit var buttonUseful: ImageButton
    private lateinit var buttonMelodySleep: Button
    private lateinit var buttonMelodyWakeUp: Button
    private lateinit var buttonMelodyAlarm: Button
    private lateinit var textButton: TextView


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_melody)


        //init
        buttonMenu = findViewById(R.id.menu_bt)
        textButton = findViewById(R.id.melody_text_button)
        buttonAlarms = findViewById(R.id.my_alarms_clock_bt)
        buttonUseful = findViewById(R.id.useful_advince_bt)
        buttonMelodySleep = findViewById(R.id.sleep_melody_bt)
        buttonMelodyWakeUp = findViewById(R.id.wake_up_melody_bt)
        buttonMelodyAlarm = findViewById(R.id.alarm_melody_bt)

        //fun
        buttonChange()
        printTextButton()
    }


    fun buttonChange(){
        buttonMenu.setOnClickListener {
            val pageMenu = Intent (this, MenuActivity::class.java)
            startActivity(pageMenu)
        }
        buttonAlarms.setOnClickListener {
            val pageAlarms = Intent (this, MyAlarmsClockActivity::class.java)
            startActivity(pageAlarms)
        }
        buttonUseful.setOnClickListener {
            val pageUseful = Intent (this, UsefulAdvinceActivity::class.java)
            startActivity(pageUseful)
        }
        buttonMelodySleep.setOnClickListener {
            val pageMelodySleep = Intent (this, SleepMelodiesActivity::class.java)
            startActivity(pageMelodySleep)
        }
        buttonMelodyWakeUp.setOnClickListener {
            val pageMelodyWakeUp = Intent (this, WakeUpMelodiesActivity::class.java)
            startActivity(pageMelodyWakeUp)
        }
        buttonMelodyAlarm.setOnClickListener {
            val pageMelodyAlarm = Intent (this, AlarmMelodiesActivity::class.java)
            startActivity(pageMelodyAlarm)
        }

    }
    //text button
    fun printTextButton() {
        textButton.text = "Melodies"

    }
}