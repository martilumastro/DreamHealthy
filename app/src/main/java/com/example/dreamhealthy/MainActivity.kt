package com.example.dreamhealthy

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager.widget.ViewPager


class MainActivity : AppCompatActivity() {

    //var for view page
    private lateinit var buttonMenu: ImageButton
    private lateinit var buttonToday: ImageButton
    private lateinit var textButton: TextView
    private lateinit var homeBtn: ImageButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //init image buttons
        buttonMenu = findViewById(R.id.menuBt)
        buttonToday = findViewById(R.id.todayBt)
        textButton = findViewById(R.id.home_text_button)
        homeBtn = findViewById(R.id.homeBt)


        //call fun
        buttonChange()
        printTextButton()
    }


    //fun for all changing page
    fun buttonChange(){
        // button hamburger --> from home to menu
        buttonMenu.setOnClickListener {
            val pageMenu = Intent (this, MenuActivity::class.java)
            startActivity(pageMenu)
        }
        // button today --> from home to today
        buttonToday.setOnClickListener {
            val pageToday = Intent (this, TodayActivity::class.java)
            startActivity(pageToday)
        }
        // button home --> NULL
        homeBtn.setOnClickListener {
            val pageHome = Intent (this, MainActivity::class.java)
            startActivity(pageHome)
        }
    }

    //text button
    fun printTextButton() {
        textButton.text = "Home"
    }
}