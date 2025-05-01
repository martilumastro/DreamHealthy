package com.example.dreamhealthy

import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager.widget.ViewPager
import com.example.dreamhealthy.Adapter.PageAdapter

class CalendarActivity : AppCompatActivity() {

    private lateinit var mViewPager: ViewPager
    private lateinit var homeBtn:ImageButton
    private lateinit var todayBtn:ImageButton
    private lateinit var menuBtn:ImageButton
    private lateinit var nPagerAdepter: PageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)

        //init view
        //mViewPager = findViewById(R.id.mviewPager)

        //button image
        homeBtn = findViewById(R.id.homeBt)
        todayBtn = findViewById(R.id.todayBt)
        menuBtn = findViewById(R.id.menuBt)
    }
}