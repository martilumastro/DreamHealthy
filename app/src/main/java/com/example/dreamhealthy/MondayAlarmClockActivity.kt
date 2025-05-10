package com.example.dreamhealthy

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.dreamhealthy.databinding.ActivityCalendarBinding
import com.example.dreamhealthy.databinding.ActivityMondayAlarmClockBinding
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
class MondayAlarmClockActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMondayAlarmClockBinding
    private lateinit var dayText: TextView
    private lateinit var goBackButton: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMondayAlarmClockBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dayText = findViewById(R.id.dayText)
        goBackButton = findViewById(R.id.go_back_bt)

        //fun
        viewThisDay()
    }

    fun buSetTime(view: View){

    }

    fun changePage(){
        goBackButton.setOnClickListener {
            val backPage = Intent(this, MyAlarmsClockActivity::class.java)
            startActivity(backPage)
        }
    }


    //fun calculated day
    fun viewThisDay(){
        val today = LocalDate.now()
        val nextMonday = today.with(java.time.temporal.TemporalAdjusters.next(DayOfWeek.MONDAY))

        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        val formattedDate = nextMonday.format(formatter)

        binding.dayText.text = formattedDate

    }
}