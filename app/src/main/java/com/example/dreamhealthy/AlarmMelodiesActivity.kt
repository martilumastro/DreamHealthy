package com.example.dreamhealthy

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class AlarmMelodiesActivity : AppCompatActivity() {
    private lateinit var buttonStandard: Button
    private lateinit var buttonNatural: Button
    private lateinit var buttonMelody: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_alarm_melodies)

        //init
        buttonStandard = findViewById(R.id.musical_standard)
        buttonNatural = findViewById(R.id.musical_natural)
        buttonMelody = findViewById(R.id.go_back_bt)

        //call function
        changePage()
    }
    //change the page
    fun changePage(){
        buttonMelody.setOnClickListener {
            val pageMelody = Intent(this, MelodyActivity::class.java)
            startActivity(pageMelody)
        }
        buttonStandard.setOnClickListener {
            val pageStandard = Intent(this, AlarmMelodiesActivity::class.java)
            startActivity(pageStandard)
        }
        buttonNatural.setOnClickListener {
            val pageNatural = Intent(this, AlarmNaturalActivity::class.java)
            startActivity(pageNatural)
        }
    }
}