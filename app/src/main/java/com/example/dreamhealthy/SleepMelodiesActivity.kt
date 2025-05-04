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

class SleepMelodiesActivity : AppCompatActivity() {
    //var
    private lateinit var melodyBt: Button
    private lateinit var buttonPlayPause: ImageButton
    var changePlayPause = false

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        //init image buttons
        melodyBt = findViewById(R.id.go_back_bt)
        buttonPlayPause = findViewById(R.id.play_pause_bt)

        buttonChange()
        changeStartPauseButton()
    }

    //fun for changing page (navbar)
    fun buttonChange()
    {
        // button myAlarmsClockBt  --> return to myAlarmsClock's page
        val buttonMelody = findViewById<Button>(R.id.go_back_bt)
        buttonMelody.setOnClickListener {
            val pageMelody = Intent(this, MelodyActivity::class.java)
            startActivity(pageMelody)
        }
    }

    //fun change start and pause button
    fun changeStartPauseButton() {
        buttonPlayPause.setOnClickListener {
            changePlayPause = !changePlayPause
            if (changePlayPause) {
                buttonPlayPause.setImageResource(R.drawable.icon_pause_music_black)
                //music play
            } else {
                buttonPlayPause.setImageResource(R.drawable.icon_play_music_black)
                //music stop
            }
        }
    }
}