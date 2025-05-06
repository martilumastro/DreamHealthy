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
    private lateinit var buttonMelodySleep: Button
    private lateinit var textButton: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_melody)


        //init
        buttonMenu = findViewById(R.id.menu_bt)
        textButton = findViewById(R.id.melody_text_button)
        buttonAlarms = findViewById(R.id.my_alarms_clock_bt)
        buttonMelodySleep = findViewById(R.id.sleep_melody_bt)

        //fun
        buttonChange()
        printTextButton()
    }


    fun buttonChange(){
        // button menu --> from melody to home
        buttonMenu.setOnClickListener {
            val pageMenu = Intent (this, MenuActivity::class.java)
            startActivity(pageMenu)
        }

        // button alarms --> from melody to alarms
        buttonAlarms.setOnClickListener {
            val pageAlarms = Intent (this, MyAlarmsClockActivity::class.java)
            startActivity(pageAlarms)
        }

        // button melodySleep --> from melody to sleep's melodies
        buttonMelodySleep.setOnClickListener {
            val pageMelodySleep = Intent (this, SleepMelodiesActivity::class.java)
            startActivity(pageMelodySleep)
        }

    }
    //text button
    fun printTextButton() {
        textButton.text = "Melodies"

    }
}