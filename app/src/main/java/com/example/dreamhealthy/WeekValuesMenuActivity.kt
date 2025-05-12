package com.example.dreamhealthy

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class WeekValuesMenuActivity : AppCompatActivity() {
    private lateinit var buttonHome: ImageButton
    private lateinit var buttonWeekValue: ImageButton
    private lateinit var buttonMenu: ImageButton
    private lateinit var textButton: TextView
    private lateinit var mondayButton: Button
    private lateinit var tuesdayButton: Button
    private lateinit var wednesdayButton: Button
    private lateinit var thrusdayButton: Button
    private lateinit var fridayButton: Button
    private lateinit var saturdayButton: Button
    private lateinit var sundayButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_week_values_menu)
        //init
        textButton = findViewById(R.id.menu_text_button)
        buttonMenu = findViewById(R.id.menuBt)
        buttonWeekValue = findViewById(R.id.week_values_bt)
        buttonHome = findViewById(R.id.homeBt)
        mondayButton = findViewById(R.id.monday_values_bt)
        tuesdayButton = findViewById(R.id.tuesday_values_bt)
        wednesdayButton = findViewById(R.id.wednesday_values_bt)
        thrusdayButton = findViewById(R.id.thursday_values_bt)
        fridayButton = findViewById(R.id.friday_values_bt)
        saturdayButton = findViewById(R.id.saturday_values_bt)
        sundayButton = findViewById(R.id.sunday_values_bt)

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
        mondayButton.setOnClickListener {
            val pageMonday = Intent (this, MondayActivity::class.java)
            startActivity(pageMonday)
        }
        tuesdayButton.setOnClickListener {
            val pageTuesday = Intent (this, TuesdayActivity::class.java)
            startActivity(pageTuesday)
        }
        wednesdayButton.setOnClickListener {
            val pageWednesday = Intent (this, WednesdayActivity::class.java)
            startActivity(pageWednesday)
        }
        thrusdayButton.setOnClickListener {
            val pageThrusday = Intent (this, ThursdayActivity::class.java)
            startActivity(pageThrusday)
        }
        fridayButton.setOnClickListener {
            val pageFriday = Intent (this, FridayActivity::class.java)
            startActivity(pageFriday)
        }
        saturdayButton.setOnClickListener {
            val pageSaturday = Intent (this, SaturdayActivity::class.java)
            startActivity(pageSaturday)
        }
        sundayButton.setOnClickListener {
            val pageSunday = Intent (this, SundayActivity::class.java)
            startActivity(pageSunday)
        }
    }

    //text button
    fun printTextButton() {
        textButton.text = "Week menu"
    }
}