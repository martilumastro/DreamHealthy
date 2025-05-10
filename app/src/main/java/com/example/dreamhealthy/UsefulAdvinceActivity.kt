package com.example.dreamhealthy

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class UsefulAdvinceActivity : AppCompatActivity() {
    private lateinit var listView: ListView
    private lateinit var list: Array<String>
    private lateinit var buttonToday: ImageButton
    private lateinit var buttonChart: ImageButton
    private lateinit var buttonMenu: ImageButton


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_useful_advince)
        buttonToday = findViewById(R.id.today_bt)
        buttonChart = findViewById(R.id.chart_bt)
        buttonMenu = findViewById(R.id.menu_bt)

        //cal fun
        listUsefulAdvince()
        changePage()
    }

    fun changePage(){
        buttonToday.setOnClickListener {
            val todayPage = Intent(this, TodayActivity::class.java)
            startActivity(todayPage)
        }
        buttonChart.setOnClickListener {
            val chartPage = Intent(this, ChartActivity::class.java)
            startActivity(chartPage)
        }
        buttonMenu.setOnClickListener {
            val menuPage = Intent(this, MenuActivity::class.java)
            startActivity(menuPage)
        }

    }

    fun listUsefulAdvince(){
        //text and visual elementslist
        list = arrayOf(
            "Take a few minutes to breathe deeply.",
            "Listen to calming music or nature sounds.",
            "Try a short guided meditation or body scan exercise.",
            "Read a few pages of a book (avoid screens!)."
        )
        listView = findViewById(R.id.list_view)

        //style
        val adapter = ArrayAdapter(
            this,
            R.layout.list_style,
            R.id.text_item,
            list
        )
        listView.adapter = adapter
    }
}