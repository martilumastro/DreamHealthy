package com.example.dreamhealthy

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class WeekValuesMenuActivity : AppCompatActivity() {
    private lateinit var buttonHome: ImageButton
    private lateinit var buttonWeekValue: ImageButton
    private lateinit var buttonMenu: ImageButton
    private lateinit var textButton: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_week_values_menu)
        //init
        textButton = findViewById(R.id.menu_text_button)
        buttonMenu = findViewById(R.id.menuBt)
        buttonWeekValue = findViewById(R.id.week_values_bt)
        buttonHome = findViewById(R.id.homeBt)

        //call fun
        buttonChange()
        printTextButton()
    }

    //fun for all changing page
    fun buttonChange(){
        // button home --> from menu to home
        buttonHome.setOnClickListener {
            val pageHome = Intent (this, MainActivity::class.java)
            startActivity(pageHome)
        }
        buttonWeekValue.setOnClickListener {
            val pageWeekValue = Intent (this, WeekValuesMenuActivity::class.java)
            startActivity(pageWeekValue)
        }
        buttonMenu.setOnClickListener {
            val pageMenu = Intent (this, MenuActivity::class.java)
            startActivity(pageMenu)
        }
    }

    //text button
    fun printTextButton() {
        textButton.text = "Week menu"
    }
}