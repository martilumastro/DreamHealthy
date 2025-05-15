package com.example.dreamhealthy

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.AlertDialog
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import android.content.SharedPreferences

@RequiresApi(Build.VERSION_CODES.O)
class MondayAlarmClockActivity : AppCompatActivity() {
    private lateinit var dayText: TextView
    private lateinit var goBackButton: Button
    private lateinit var wakeUpHourTextView: TextView
    private lateinit var alarmMelodyTextView: TextView
    private lateinit var sleepMelodyTextView: TextView
    private lateinit var wakeUpMelodyTextView: TextView
    private lateinit var sleepUpHourTextView: TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_monday_alarm_clock)
        enableEdgeToEdge()

        dayText = findViewById(R.id.dayText)
        goBackButton = findViewById(R.id.go_back_bt)
        wakeUpHourTextView = findViewById(R.id.wake_up_hour)
        alarmMelodyTextView = findViewById(R.id.alarm_melody)
        sleepMelodyTextView = findViewById(R.id.sleep_melody)
        wakeUpMelodyTextView = findViewById(R.id.wake_up_melody)
        sleepUpHourTextView = findViewById(R.id.sleep_hour)


        wakeUpHourTextView.setOnClickListener {
            val currentText = wakeUpHourTextView.text.toString()
            if (currentText == "--:--") {
                // TimePickerDialog for setting the alarm
                val calendar = Calendar.getInstance()
                val hour = calendar.get(Calendar.HOUR_OF_DAY)
                val minute = calendar.get(Calendar.MINUTE)

                val timePickerDialog = TimePickerDialog(
                    this,
                    { _, selectedHour, selectedMinute ->
                        // Set alarm and new text
                        wakeUpHourTextView.text = String.format("%02d:%02d", selectedHour, selectedMinute)
                        //alarm text
                        alarmMelodyTextView.text = "Standard alarm"
                        sleepMelodyTextView.text = "Standard: Rowboat"
                        wakeUpMelodyTextView.text = "Standard: Birds chirping"
                        //sleep hour
                        val sleepCalendar = Calendar.getInstance().apply {
                            set(Calendar.HOUR_OF_DAY, selectedHour)
                            set(Calendar.MINUTE, selectedMinute)
                            add(Calendar.HOUR_OF_DAY, -8)
                        }
                        val sleepHour = sleepCalendar.get(Calendar.HOUR_OF_DAY)
                        val sleepMinute = sleepCalendar.get(Calendar.MINUTE)

                        //sleep text
                        sleepUpHourTextView.text = String.format("%02d:%02d", sleepHour, sleepMinute)
                        //set alarm
                        setAlarm(selectedHour, selectedMinute)
                        //set sleep melody
                        setSleepMelody(sleepHour, sleepMinute)
                        //set pre wake melody
                        setPreWakeMelody(selectedHour, selectedMinute)
                    },
                    hour,
                    minute,
                    true
                )
                timePickerDialog.show()
            } else {
                AlertDialog.Builder(this)
                    .setTitle("Delete this alarm!")
                    .setMessage("Do you want to delete the alarm set for: $currentText or set another one?")
                    .setPositiveButton("Delete") { _, _ ->
                        // delete alarm
                        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
                        val intentDelete = Intent(this, AlarmReceiver::class.java)
                        val pendingIntent = PendingIntent.getBroadcast(
                            this,
                            0,
                            intentDelete,
                            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
                        )
                        alarmManager.cancel(pendingIntent)

                        // standard text
                        wakeUpHourTextView.text = "--:--"
                        alarmMelodyTextView.text = ""
                        sleepMelodyTextView.text = ""
                        wakeUpMelodyTextView.text = ""
                        sleepUpHourTextView.text = "--:--"
                        Toast.makeText(this, "Alarm delete", Toast.LENGTH_SHORT).show()
                    }
                    .setNegativeButton("Cancel", null)
                    .show()
                //save delete alarm for toggle button in my alarms clock
                val prefsMondayDelete = getSharedPreferences("alarms", MODE_PRIVATE)
                prefsMondayDelete.edit()
                    .putBoolean("monday_alarm_set", false)
                    .remove("monday_alarm_time")
                    .apply()
            }
        }

        //fun
        viewThisDay()
        changePage()

    }



    fun changePage() {
        goBackButton.setOnClickListener {
            val backPage = Intent(this, MyAlarmsClockActivity::class.java)
            startActivity(backPage)
        }
    }

    private fun setAlarm(hour: Int, minute: Int) {
        //calendar object with hour and minute chosen by the user
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)

            //Next monday if today is not monday
            while (get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
                add(Calendar.DATE, 1)
            }
            //Next monday if today is monday and time is before current time
            if (before(Calendar.getInstance())) {
                add(Calendar.WEEK_OF_YEAR, 1)
            }
        }

        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        // check permission SCHEDULE_EXACT_ALARM
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && !alarmManager.canScheduleExactAlarms()) {
            val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM).apply {
                data = Uri.parse("package:$packageName")
            }
            startActivity(intent)
            Toast.makeText(this, "You give permission to alarms", Toast.LENGTH_LONG).show()
            return
        }

        //connection with alarmReceiver for music
        val intent = Intent(this, AlarmReceiver::class.java).apply {
            putExtra("standard alarm", "standard_alarm_melody.mp3")
        }
        //Broadcast: alarm start even if the app is closed
        val pendingIntent = PendingIntent.getBroadcast(
            this,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        //if user click on the notification open app
        val showIntent = Intent(this, MondayAlarmClockActivity::class.java)
        val showPendingIntent = PendingIntent.getActivity(
            this,
            1,
            showIntent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        try {
            //alarm visible
            alarmManager.setAlarmClock(
                AlarmManager.AlarmClockInfo(calendar.timeInMillis, showPendingIntent),
                pendingIntent
            )
            //confirm set alarm
            Toast.makeText(this, "Alarm set for: ${String.format("%02d:%02d", hour, minute)}", Toast.LENGTH_SHORT).show()
        } catch (e: SecurityException) {
            e.printStackTrace()
            //if not granted permission
            Toast.makeText(this, "Error: Allow alarms in settings", Toast.LENGTH_LONG).show()
        }
        //save state and clock of the alarm for toggle button in my alarms clock
        val prefsMonday = getSharedPreferences("alarms", MODE_PRIVATE)
        prefsMonday.edit()
            .putBoolean("monday_alarm_set", true)
            .putString("monday_alarm_time", String.format("%02d:%02d", hour, minute))
            .apply()
    }
    // fun sleep melody
    private fun setSleepMelody(hour: Int, minute: Int) {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
            if (before(Calendar.getInstance())) add(Calendar.DATE, 1)
        }

        val intent = Intent(this, AlarmReceiver::class.java).apply {
            putExtra("standard alarm", "rowboat.mp3")
        }

        val pendingIntent = PendingIntent.getBroadcast(
            this,
            1,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
    }

    private fun setPreWakeMelody(wakeHour: Int, wakeMinute: Int) {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, wakeHour)
            set(Calendar.MINUTE, wakeMinute)
            add(Calendar.MINUTE, -15)
            set(Calendar.SECOND, 0)
            if (before(Calendar.getInstance())) add(Calendar.DATE, 1)
        }

        val intent = Intent(this, AlarmReceiver::class.java).apply {
            putExtra("standard alarm", "birds_chirping_melody_wake.mp3")
        }

        val pendingIntent = PendingIntent.getBroadcast(
            this,
            2,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
    }


    //fun calculated day
    fun viewThisDay() {
        val today = LocalDate.now()
        val nextMonday = today.with(java.time.temporal.TemporalAdjusters.next(DayOfWeek.MONDAY))

        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        val formattedDate = nextMonday.format(formatter)

        dayText.text = formattedDate
    }
}