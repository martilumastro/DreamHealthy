package com.example.dreamhealthy

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Button
//import android.widget.TextView

class ChartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_chart) // setta xml per la parte grafica
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main))
        { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets

        }
        // bottom from Chart to Menu
       //val buttonHome = findViewById<Button>(R.id.home_button)
       //buttonHome.setOnClickListener {
        //   val pageHome = Intent(this, MainActivity::class.java)
         //  startActivity(pageHome)
       //}
        //val buttonToday = findViewById<Button>(R.id.today_button)
        //buttonToday.setOnClickListener {
        //val pageToday = Intent(this, TodayActivity::class.java)
         //   startActivity(pageToday)
        //}

    }
}