package com.example.dreamhealthy

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class WakeUpNatureActivity : AppCompatActivity() {
    private lateinit var buttonBalance: Button
    private lateinit var buttonNature: Button
    private lateinit var buttonMount: Button
    private lateinit var buttonWeather: Button
    private lateinit var buttonMelody: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_wake_up_nature)

        //init
        buttonMelody = findViewById(R.id.go_back_bt)
        buttonNature = findViewById(R.id.musical_nature)
        buttonBalance = findViewById(R.id.musical_meditation)
        buttonMount = findViewById(R.id.musical_mountain)
        buttonWeather = findViewById(R.id.musical_weather)


        //call function
        changePage()
    }

    fun changePage(){
        buttonMelody.setOnClickListener {
            val pageMelody = Intent(this, MelodyActivity::class.java)
            startActivity(pageMelody)
        }
        buttonBalance.setOnClickListener {
            val pageBalance = Intent(this, WakeUpMelodiesActivity::class.java)
            startActivity(pageBalance)
        }
        buttonNature.setOnClickListener {
            val pageNature = Intent(this, WakeUpNatureActivity::class.java)
            startActivity(pageNature)
        }
        buttonMount.setOnClickListener{
            val pageMount = Intent(this, WakeUpMountActivity::class.java)
            startActivity(pageMount)
        }
        buttonWeather.setOnClickListener {
            val pageWeather = Intent(this, WakeUpWeatherActivity::class.java)
            startActivity(pageWeather)
        }
    }
}