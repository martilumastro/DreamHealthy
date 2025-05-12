package com.example.dreamhealthy

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageButton
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class MorningUsefulAdvinceActivity : AppCompatActivity() {
    private lateinit var listView: ListView
    private lateinit var list: Array<String>
    private lateinit var buttonToday: ImageButton
    private lateinit var buttonChart: ImageButton
    private lateinit var buttonMenu: ImageButton
    private lateinit var buttonBack: ImageButton
    private lateinit var buttonNext: ImageButton
    private lateinit var buttonFoodList: Button
    private lateinit var buttonToDoList: Button
    private lateinit var buttonRelaxList: Button
    private lateinit var buttonMorningList: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_morning_useful_advince)
        buttonToday = findViewById(R.id.today_bt)
        buttonChart = findViewById(R.id.chart_bt)
        buttonMenu = findViewById(R.id.menu_bt)
        buttonBack = findViewById(R.id.back_page)
        buttonNext = findViewById(R.id.next_page)
        buttonFoodList = findViewById(R.id.food_list)
        buttonToDoList = findViewById(R.id.to_do_list)
        buttonRelaxList = findViewById(R.id.relax_list)
        buttonMorningList = findViewById(R.id.morning_list)

        //cal fun
        listUsefulAdvince()
        changePage()
    }

    //change page
    fun changePage(){
        buttonToday.setOnClickListener {
            val todayPage = Intent(this, MondayActivity::class.java)
            startActivity(todayPage)
        }
        buttonChart.setOnClickListener {
            val chartPage = Intent(this, MondayChartActivity::class.java)
            startActivity(chartPage)
        }
        buttonMenu.setOnClickListener {
            val menuPage = Intent(this, MenuActivity::class.java)
            startActivity(menuPage)
        }
        buttonBack.setOnClickListener {
            val backPage = Intent(this, ToDoUsefulAdvinceActivity::class.java)
            startActivity(backPage)
        }
        buttonNext.setOnClickListener {
            val nextPage = Intent(this, UsefulAdvinceActivity::class.java)
            startActivity(nextPage)
        }
        buttonFoodList.setOnClickListener {
            val foodPage = Intent(this, FoodUsefulAdvinceActivity::class.java)
            startActivity(foodPage)
        }
        buttonToDoList.setOnClickListener {
            val toDoPage = Intent(this, ToDoUsefulAdvinceActivity::class.java)
            startActivity(toDoPage)
        }
        buttonRelaxList.setOnClickListener {
            val relaxPage = Intent(this, UsefulAdvinceActivity::class.java)
            startActivity(relaxPage)
        }
        buttonMorningList.setOnClickListener {
            val morningPage = Intent(this, MorningUsefulAdvinceActivity::class.java)
            startActivity(morningPage)
        }

    }

    //fun list
    fun listUsefulAdvince(){
        //text and visual elementslist
        list = arrayOf(
            "Try to wake up at the same time every day.",
            "Get sunlight exposure as early as possible.",
            "Do some gentle stretching to wake up your body.",
            "Avoid hitting the snooze button too often."
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