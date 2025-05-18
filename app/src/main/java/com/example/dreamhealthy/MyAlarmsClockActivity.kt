package com.example.dreamhealthy

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MyAlarmsClockActivity : AppCompatActivity() {

    private lateinit var buttonWeekValue: ImageButton
    private lateinit var buttonMenu: ImageButton
    private lateinit var buttonMelody: ImageButton
    private lateinit var buttonUseful: ImageButton
    private lateinit var buttonAlarmMonday: Button
    private lateinit var buttonAlarmTuesday: Button
    private lateinit var buttonAlarmWednesday: Button
    private lateinit var buttonAlarmThursday: Button
    private lateinit var buttonAlarmFriday: Button
    private lateinit var buttonAlarmSaturday: Button
    private lateinit var buttonAlarmSunday: Button

    private lateinit var mondayHour: TextView
    private lateinit var switchMonday: Switch

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_my_alarms_clock)


        //init image buttons
        buttonMenu = findViewById(R.id.menu_bt)
        buttonWeekValue = findViewById(R.id.week_values_bt)
        buttonUseful = findViewById(R.id.useful_advince_bt)
        buttonMelody = findViewById(R.id.melody_bt)
        buttonAlarmMonday = findViewById(R.id.monday_bt)
        buttonAlarmTuesday = findViewById(R.id.tuesday_bt)
        buttonAlarmWednesday = findViewById(R.id.wednesday_bt)
        buttonAlarmThursday = findViewById(R.id.thursday_bt)
        buttonAlarmFriday = findViewById(R.id.friday_bt)
        buttonAlarmSaturday = findViewById(R.id.saturday_bt)
        buttonAlarmSunday = findViewById(R.id.sunday_bt)

        mondayHour = findViewById(R.id.monday_hour)
        switchMonday = findViewById(R.id.switch_monday)

        //call fun
        buttonChange()
        toggleMonday()
    }

    fun buttonChange() {
        // button hamburger --> from alarms to menu
        buttonMenu.setOnClickListener {
            val pageMenu = Intent(this, MenuActivity::class.java)
            startActivity(pageMenu)
        }
        buttonWeekValue.setOnClickListener {
            val pageWeekValue = Intent (this, WeekValuesMenuActivity::class.java)
            startActivity(pageWeekValue)
        }
        buttonUseful.setOnClickListener {
            val pageUseful = Intent (this, UsefulAdvinceActivity::class.java)
            startActivity(pageUseful)
        }
        buttonMelody.setOnClickListener {
            val pageMelody = Intent(this, MelodyActivity::class.java)
            startActivity(pageMelody)
        }
        buttonAlarmMonday.setOnClickListener {
            val pageAlarm = Intent(this, MondayAlarmClockActivity::class.java)
            startActivity(pageAlarm)
        }
        buttonAlarmTuesday.setOnClickListener {
            val pageAlarm = Intent(this, TuesdayAlarmClockActivity::class.java)
            startActivity(pageAlarm)
        }
        buttonAlarmWednesday.setOnClickListener {
            val pageAlarm = Intent(this, WednesdayAlarmClockActivity::class.java)
            startActivity(pageAlarm)
        }
        buttonAlarmThursday.setOnClickListener {
            val pageAlarm = Intent(this, ThursdayAlarmClockActivity::class.java)
            startActivity(pageAlarm)
        }
        buttonAlarmFriday.setOnClickListener {
            val pageAlarm = Intent(this, FridayAlarmClockActivity::class.java)
            startActivity(pageAlarm)
        }
        buttonAlarmSaturday.setOnClickListener {
            val pageAlarm = Intent(this, SaturdayAlarmClockActivity::class.java)
            startActivity(pageAlarm)
        }
        buttonAlarmSunday.setOnClickListener {
            val pageAlarm = Intent(this, SundayAlarmClockActivity::class.java)
            startActivity(pageAlarm)
        }


    }

    fun toggleMonday() {
        val prefsMonday = getSharedPreferences("alarms", MODE_PRIVATE)
        val isMondayAlarmSet = prefsMonday.getBoolean("monday_alarm_set", false)
        val mondayAlarmTime = prefsMonday.getString("monday_alarm_time", "--:--")

        mondayHour.text = mondayAlarmTime
        switchMonday.isChecked = isMondayAlarmSet

        //activation and deactivation switch
        switchMonday.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // open MondayAlarmClockActivity
                val intent = Intent(this, MondayAlarmClockActivity::class.java)
                startActivity(intent)
            } else {
                // deactive monday alarm
                val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
                val intentDelete = Intent(this, AlarmReceiver::class.java)
                val pendingIntent = PendingIntent.getBroadcast(
                    this,
                    0,
                    intentDelete,
                    PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
                )
                alarmManager.cancel(pendingIntent)

                // remove alarm
                prefsMonday.edit()
                    .putBoolean("monday_alarm_set", false)
                    .remove("monday_alarm_time")
                    .apply()

                mondayHour.text = "--:--"
                Toast.makeText(this, "Monday's alarm deactivated", Toast.LENGTH_SHORT).show()
            }
        }
    }


}