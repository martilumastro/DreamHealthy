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

    //var for call "androidx.viewpager.widget.ViewPager"
    //var for view page
    private lateinit var homeBtn: ImageButton
    private lateinit var todayBtn: ImageButton
    private lateinit var menuBtn: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //init image buttons
        homeBtn = findViewById(R.id.homeBt)
        todayBtn = findViewById(R.id.todayBt)
        menuBtn = findViewById(R.id.menuBt)


        //call fun
        buttonChange()
        printTextButton()
    }


    //fun for all changing page
    fun buttonChange(){
        // button hamburger --> from home to menu
        val buttonMenu = findViewById<ImageButton>(R.id.menuBt)
        buttonMenu.setOnClickListener {
            val pageMenu = Intent (this, MenuActivity::class.java)
            startActivity(pageMenu)
        }
        // button today --> from home to today
        val buttonToday = findViewById<ImageButton>(R.id.todayBt)
        buttonToday.setOnClickListener {
            val pageToday = Intent (this, TodayActivity::class.java)
            startActivity(pageToday)
        }
    }

    //text button
    fun printTextButton() {
        val textButton = findViewById<TextView>(R.id.home_text_button)
        textButton.text = "Home"
    }
}