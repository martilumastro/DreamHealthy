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
    private lateinit var buttonBalance: Button
    private lateinit var buttonSea: Button
    private lateinit var buttonForest: Button
    private lateinit var buttonDreams: Button
    private lateinit var buttonMelody: Button
    private lateinit var buttonPlayPause: ImageButton
    private var changePlayPause = false

    data class Melody(
        val audioPath: String,
        val imageReId: Int
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sleep_melodies)

        //init
        buttonPlayPause = findViewById(R.id.play_pause_bt)
        buttonMelody = findViewById(R.id.go_back_bt)
        buttonSea = findViewById(R.id.musical_sea)
        buttonForest = findViewById(R.id.musical_nature)
        buttonDreams = findViewById(R.id.musical_night)
        buttonBalance = findViewById(R.id.musical_meditation)


        //melodies' list
        /*val Melodies = listOf(
            Melody("asset://rowboat.mp3", R.drawable.img_rowboat_white),
            Melody("asset://sea_swimming.mp3", R.drawable.img_sea_swimming_white),
            Melody("asset://sea_waves.mp3", R.drawable.img_waves_white),
            Melody("asset://sea_waves_light_melody.mp3", R.drawable.img_sea_waves_light_white),
            Melody("asset://sea_waves_with_birds.mp3", R.drawable.sea_waves_with_birds),
            Melody("asset://strong_wind_sea.mp3", R.drawable.img_strong_wind_white),
        )*/

        //call function
        buttonChange()
        changeStartPauseButton()
    }

    //fun for changing page (navbar)
    fun buttonChange()
    {
        // button myAlarmsClockBt  --> return to myAlarmsClock's page
        buttonMelody.setOnClickListener {
            val pageMelody = Intent(this, MelodyActivity::class.java)
            startActivity(pageMelody)
        }
        buttonBalance.setOnClickListener {
            val pageBalance = Intent(this, SleepMelodiesActivity::class.java)
            startActivity(pageBalance)
        }
        buttonSea.setOnClickListener {
            val pageSea = Intent(this, SleepSeaActivity::class.java)
            startActivity(pageSea)
        }
        buttonForest.setOnClickListener {
            val pageForest = Intent(this, SleepForestActivity::class.java)
            startActivity(pageForest)
        }
        buttonDreams.setOnClickListener {
            val pageDreams = Intent(this, SleepDreamsActivity::class.java)
            startActivity(pageDreams)
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