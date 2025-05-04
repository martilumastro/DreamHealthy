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

    private lateinit var melodyBtn: ImageButton
    private lateinit var todayBtn: ImageButton
    private lateinit var menuBtn: ImageButton

    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_my_alarms_clock)


            //init image buttons
        melodyBtn = findViewById(R.id.melody_bt)
        todayBtn = findViewById(R.id.today_bt)
        menuBtn = findViewById(R.id.menu_bt)

        //call fun
        buttonChange()
    }

    fun buttonChange() {
        // button hamburger --> from alarms to menu
        val buttonMenu = findViewById<ImageButton>(R.id.menu_bt)
        buttonMenu.setOnClickListener {
            val pageMenu = Intent(this, MenuActivity::class.java)
            startActivity(pageMenu)
        }
        // button today --> from home to today
        val buttonToday = findViewById<ImageButton>(R.id.today_bt)
        buttonToday.setOnClickListener {
            val pageToday = Intent(this, TodayActivity::class.java)
            startActivity(pageToday)
        }
        // button melody --> from alarms to melody
        val buttonMelody = findViewById<ImageButton>(R.id.melody_bt)
        buttonMelody.setOnClickListener {
            val pageMelody = Intent(this, MelodyActivity::class.java)
            startActivity(pageMelody)
        }
    }
}
