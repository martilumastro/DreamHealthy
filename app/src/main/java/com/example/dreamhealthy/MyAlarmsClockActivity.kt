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
    private lateinit var tuesdayHour: TextView
    private lateinit var switchTuesday: Switch
    private lateinit var wednesdayHour: TextView
    private lateinit var switchWednesday: Switch
    private lateinit var thursdayHour: TextView
    private lateinit var switchThursday: Switch
    private lateinit var fridayHour: TextView
    private lateinit var switchFriday: Switch
    private lateinit var saturdayHour: TextView
    private lateinit var switchSaturday: Switch
    private lateinit var sundayHour: TextView
    private lateinit var switchSunday: Switch


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
        tuesdayHour = findViewById(R.id.tuesday_hour)
        switchTuesday = findViewById(R.id.switch_tuesday)
        wednesdayHour = findViewById(R.id.wednesday_hour)
        switchWednesday = findViewById(R.id.switch_wednesday)
        thursdayHour = findViewById(R.id.thursday_hour)
        switchThursday = findViewById(R.id.switch_thursday)
        fridayHour = findViewById(R.id.friday_hour)
        switchFriday = findViewById(R.id.switch_friday)
        saturdayHour = findViewById(R.id.saturday_hour)
        switchSaturday = findViewById(R.id.switch_saturday)
        sundayHour = findViewById(R.id.sunday_hour)
        switchSunday = findViewById(R.id.switch_sunday)

        //call fun
        buttonChange()
        toggleMonday()
        toggleTuesday()
        toggleWednesday()
        toggleThursday()
        toggleFriday()
        toggleSaturday()
        toggleSunday()
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

    fun toggleTuesday() {
        val prefsTuesday = getSharedPreferences("alarms", MODE_PRIVATE)
        val isTuesdayAlarmSet = prefsTuesday.getBoolean("tuesday_alarm_set", false)
        val tuesdayAlarmTime = prefsTuesday.getString("tuesday_alarm_time", "--:--")
        tuesdayHour.text = tuesdayAlarmTime
        switchTuesday.isChecked = isTuesdayAlarmSet
         //activation and deactivation switch
        switchTuesday.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // open TuesdayAlarmClockActivity
                val intent = Intent(this, TuesdayAlarmClockActivity::class.java)
                startActivity(intent)
            } else {
                // deactive tuesday alarm
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
                prefsTuesday.edit()
                    .putBoolean("tuesday_alarm_set", false)
                    .remove("tuesday_alarm_time")
                    .apply()

                tuesdayHour.text = "--:--"
                Toast.makeText(this, "Tuesday's alarm deactivated", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun toggleWednesday() {
        val prefsWednesday = getSharedPreferences("alarms", MODE_PRIVATE)
        val isWednesdayAlarmSet = prefsWednesday.getBoolean("wednesday_alarm_set", false)
        val wednesdayAlarmTime = prefsWednesday.getString("wednesday_alarm_time", "--:--")
        wednesdayHour.text = wednesdayAlarmTime
        switchWednesday.isChecked = isWednesdayAlarmSet
        //activation and deactivation switch
        switchWednesday.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // open TuesdayAlarmClockActivity
                val intent = Intent(this, WednesdayAlarmClockActivity::class.java)
                startActivity(intent)
            } else {
                // deactive tuesday alarm
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
                prefsWednesday.edit()
                    .putBoolean("wednesday_alarm_set", false)
                    .remove("wednesday_alarm_time")
                    .apply()

                wednesdayHour.text = "--:--"
                Toast.makeText(this, "Wednesday's alarm deactivated", Toast.LENGTH_SHORT).show()
            }
        }
    }
    fun toggleThursday(){
        val prefsThursday = getSharedPreferences("alarms", MODE_PRIVATE)
        val isThursdayAlarmSet = prefsThursday.getBoolean("thursday_alarm_set", false)
        val thursdayAlarmTime = prefsThursday.getString("thursday_alarm_time", "--:--")
        thursdayHour.text = thursdayAlarmTime
        switchThursday.isChecked = isThursdayAlarmSet
        //activation and deactivation switch
        switchThursday.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // open TuesdayAlarmClockActivity
                val intent = Intent(this, ThursdayAlarmClockActivity::class.java)
                startActivity(intent)
            } else {
                // deactive tuesday alarm
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
                prefsThursday.edit()
                    .putBoolean("thursday_alarm_set", false)
                    .remove("thursday_alarm_time")
                    .apply()

                thursdayHour.text = "--:--"
                Toast.makeText(this, "Thursday's alarm deactivated", Toast.LENGTH_SHORT).show()
            }
        }
    }
    fun toggleFriday(){
        val prefsFriday = getSharedPreferences("alarms", MODE_PRIVATE)
        val isFridayAlarmSet = prefsFriday.getBoolean("friday_alarm_set", false)
        val fridayAlarmTime = prefsFriday.getString("friday_alarm_time", "--:--")
        fridayHour.text = fridayAlarmTime
        switchFriday.isChecked = isFridayAlarmSet
        //activation and deactivation switch
        switchFriday.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // open TuesdayAlarmClockActivity
                val intent = Intent(this, FridayAlarmClockActivity::class.java)
                startActivity(intent)
            } else {
                // deactive tuesday alarm
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
                prefsFriday.edit()
                    .putBoolean("friday_alarm_set", false)
                    .remove("friday_alarm_time")
                    .apply()

                fridayHour.text = "--:--"
                Toast.makeText(this, "Friday's alarm deactivated", Toast.LENGTH_SHORT).show()
            }
        }
    }
    fun toggleSaturday(){
        val prefsSaturday = getSharedPreferences("alarms", MODE_PRIVATE)
        val isSaturdayAlarmSet = prefsSaturday.getBoolean("saturday_alarm_set", false)
        val saturdayAlarmTime = prefsSaturday.getString("saturday_alarm_time", "--:--")
        saturdayHour.text = saturdayAlarmTime
        switchSaturday.isChecked = isSaturdayAlarmSet
        //activation and deactivation switch
        switchSaturday.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // open TuesdayAlarmClockActivity
                val intent = Intent(this, SaturdayAlarmClockActivity::class.java)
                startActivity(intent)
            } else {
                // deactive tuesday alarm
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
                prefsSaturday.edit()
                    .putBoolean("saturday_alarm_set", false)
                    .remove("saturday_alarm_time")
                    .apply()

                saturdayHour.text = "--:--"
                Toast.makeText(this, "Saturday's alarm deactivated", Toast.LENGTH_SHORT).show()
            }
        }
    }
    fun toggleSunday(){
        val prefsSunday = getSharedPreferences("alarms", MODE_PRIVATE)
        val isSundayAlarmSet = prefsSunday.getBoolean("sunday_alarm_set", false)
        val sundayAlarmTime = prefsSunday.getString("sunday_alarm_time", "--:--")
        sundayHour.text = sundayAlarmTime
        switchSunday.isChecked = isSundayAlarmSet
        //activation and deactivation switch
        switchSunday.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // open TuesdayAlarmClockActivity
                val intent = Intent(this, SundayAlarmClockActivity::class.java)
                startActivity(intent)
            } else {
                // deactive tuesday alarm
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
                prefsSunday.edit()
                    .putBoolean("sunday_alarm_set", false)
                    .remove("sunday_alarm_time")
                    .apply()
                sundayHour.text = "--:--"
                Toast.makeText(this, "Sunday's alarm deactivated", Toast.LENGTH_SHORT).show()
            }
        }
    }
}