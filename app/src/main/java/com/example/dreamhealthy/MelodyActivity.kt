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

class MelodyActivity : AppCompatActivity() {

    //var
    private lateinit var menuBtn: ImageButton
    private lateinit var alarmsButton: ImageButton

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_melody)

        //init image buttons
        //init image buttons
        menuBtn = findViewById(R.id.menu_bt)
        alarmsButton = findViewById(R.id.my_alarms_clock_bt)

        //fun for changing page
        buttonChange()
    }


    fun buttonChange(){
        // button menu --> from melody to home
        val buttonMenu = findViewById<ImageButton>(R.id.menu_bt)
        buttonMenu.setOnClickListener {
            val pageMenu = Intent (this, MenuActivity::class.java)
            startActivity(pageMenu)
        }

        // button alarms --> from melody to alarms
        val buttonAlarms = findViewById<ImageButton>(R.id.my_alarms_clock_bt)
        buttonAlarms.setOnClickListener {
            val pageAlarms = Intent (this, MyAlarmsClockActivity::class.java)
            startActivity(pageAlarms)
        }

    }
}