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

    private lateinit var buttonToday: ImageButton
    private lateinit var buttonMenu: ImageButton
    private lateinit var buttonMelody: ImageButton

    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_my_alarms_clock)


            //init image buttons
        buttonMenu = findViewById(R.id.menu_bt)
        buttonToday = findViewById(R.id.today_bt)
        buttonMelody = findViewById(R.id.melody_bt)
        //call fun
        buttonChange()
    }

    fun buttonChange() {
        // button hamburger --> from alarms to menu
        buttonMenu.setOnClickListener {
            val pageMenu = Intent(this, MenuActivity::class.java)
            startActivity(pageMenu)
        }
        // button today --> from home to today
        buttonToday.setOnClickListener {
            val pageToday = Intent(this, TodayActivity::class.java)
            startActivity(pageToday)
        }
        // button melody --> from alarms to melody
        buttonMelody.setOnClickListener {
            val pageMelody = Intent(this, MelodyActivity::class.java)
            startActivity(pageMelody)
        }
    }
}
