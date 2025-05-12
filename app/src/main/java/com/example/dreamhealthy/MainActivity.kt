package com.example.dreamhealthy

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager.widget.ViewPager
import java.time.DayOfWeek
import java.time.LocalDate


@RequiresApi(Build.VERSION_CODES.O)
class MainActivity : AppCompatActivity() {
    //val for find today
    private val today = LocalDate.now()
    private val dayOfWeek = today.dayOfWeek


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // go every time in today page
        when (dayOfWeek) {
            DayOfWeek.MONDAY -> startActivity(Intent(this, TodayActivity::class.java))
            DayOfWeek.TUESDAY -> startActivity(Intent(this, TuesdayActivity::class.java))
            DayOfWeek.WEDNESDAY -> startActivity(Intent(this, WednesdayActivity::class.java))
            DayOfWeek.THURSDAY -> startActivity(Intent(this, ThursdayActivity::class.java))
            DayOfWeek.FRIDAY -> startActivity(Intent(this, FridayActivity::class.java))
            DayOfWeek.SATURDAY -> startActivity(Intent(this, SaturdayActivity::class.java))
            DayOfWeek.SUNDAY -> startActivity(Intent(this, SundayActivity::class.java))
        }

        finish()
    }
}