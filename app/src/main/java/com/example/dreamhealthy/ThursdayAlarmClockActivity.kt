package com.example.dreamhealthy


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
import androidx.appcompat.app.AppCompatActivity
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class ThursdayAlarmClockActivity : AppCompatActivity() {
    private lateinit var dayText: TextView
    private lateinit var goBackButton: Button
    private lateinit var sleepMelodyButton: Button
    private lateinit var wakeMelodyButton: Button
    private lateinit var alarmMelodyButton: Button

    private lateinit var wakeUpHourTextView: TextView
    private lateinit var alarmMelodyTextView: TextView
    private lateinit var sleepMelodyTextView: TextView
    private lateinit var wakeUpMelodyTextView: TextView
    private lateinit var sleepUpHourTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_thursday_alarm_clock)

        dayText = findViewById(R.id.dayText)
        goBackButton = findViewById(R.id.go_back_bt)
        sleepMelodyButton = findViewById(R.id.sleep_melody_bt)
        wakeMelodyButton = findViewById(R.id.wake_melody_bt)
        alarmMelodyButton = findViewById(R.id.alarm_melody_bt)

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
                        //save data
                        val prefsThursday = getSharedPreferences("alarms", MODE_PRIVATE).edit()
                        prefsThursday.putBoolean("thursday_alarm_set", true)
                        prefsThursday.putString("thursday_alarm_time", String.format("%02d:%02d", selectedHour, selectedMinute))
                        prefsThursday.putString("thursday_sleep_hour", String.format("%02d:%02d", sleepHour, sleepMinute))
                        prefsThursday.putString("thursday_alarm_melody", alarmMelodyTextView.text.toString())
                        prefsThursday.putString("thursday_sleep_melody", sleepMelodyTextView.text.toString())
                        prefsThursday.putString("thursday_wake_melody", wakeUpMelodyTextView.text.toString())
                        prefsThursday.apply()
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
                        //save delete alarm for toggle button in my alarms clock
                        val prefsThursdayDelete = getSharedPreferences("alarms", MODE_PRIVATE)
                        prefsThursdayDelete.edit()
                            .putBoolean("thursday_alarm_set", false)
                            .remove("thursday_alarm_time")
                            .apply()
                    }
                    .setNegativeButton("Cancel", null)
                    .show()
            }
        }
        //fun
        viewThisDay()
        changePage()
        onResume()
    }

    fun changePage() {
        goBackButton.setOnClickListener {
            val backPage = Intent(this, MyAlarmsClockActivity::class.java)
            startActivity(backPage)
        }
        sleepMelodyButton.setOnClickListener {
            val sleepPage = Intent(this, SleepMelodiesActivity::class.java)
            startActivity(sleepPage)
        }
        wakeMelodyButton.setOnClickListener {
            val wakePage = Intent(this, WakeUpMelodiesActivity::class.java)
            startActivity(wakePage)
        }
        alarmMelodyButton.setOnClickListener {
            val alarmPage = Intent(this, AlarmMelodiesActivity::class.java)
            startActivity(alarmPage)
        }
    }

    private fun setAlarm(hour: Int, minute: Int) {
        //calendar object with hour and minute chosen by the user
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)

            //Next Thursday if today is not Thursday
            while (get(Calendar.DAY_OF_WEEK) != Calendar.THURSDAY) {
                add(Calendar.DATE, 1)
            }
            //Next Thursday if today is Thursday and time is before current time
            if (before(Calendar.getInstance())) {
                add(Calendar.WEEK_OF_YEAR, 1)
            }
        }

        //connection with alarmReceiver for music
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && !alarmManager.canScheduleExactAlarms()) {
            val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM).apply {
                data = Uri.parse("package:$packageName")
            }
            startActivity(intent)
            Toast.makeText(this, "You need to give permission to alarms", Toast.LENGTH_LONG).show()
            return
        }

        //Read the saved melody for alarm
        //first save melody for thursday
        MelodyStorageManager.saveMelody(this, "Thursday", "alarm", "music_alarm_melody1.mp3")
        //read name of the melody
        val melodyPath = MelodyStorageManager.getMelody(this, "Thursday", "alarm")
        //standard if melody is null
        val alarmMelody = melodyPath ?: "standard_alarm_melody.mp3" // se nulla, usa quella standard

        // send the melody to the alarmReceiver
        val intent = Intent(this, AlarmReceiver::class.java).apply {
            putExtra("alarm_melody_file", alarmMelody)
        }
        //Creating a PendingIntent upgradeable
        val pendingIntent = PendingIntent.getBroadcast(
            this,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        //intent for open ThursdayAlarmClockActivity
        val showIntent = Intent(this, ThursdayAlarmClockActivity::class.java)
        val showPendingIntent = PendingIntent.getActivity(
            this,
            1,
            showIntent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        //set alarm with alarmManager
        try {
            alarmManager.setAlarmClock(
                AlarmManager.AlarmClockInfo(calendar.timeInMillis, showPendingIntent),
                pendingIntent
            )
            Toast.makeText(this, "Alarm set for: ${String.format("%02d:%02d", hour, minute)}", Toast.LENGTH_SHORT).show()
        } catch (e: SecurityException) {
            e.printStackTrace()
            Toast.makeText(this, "Error: Allow alarms in settings", Toast.LENGTH_LONG).show()
        }

        // Save on SharedPreferences ("alarms")
        val prefsThursday = getSharedPreferences("alarms", MODE_PRIVATE)
        prefsThursday.edit()
            //active alarm and format alarm
            .putBoolean("thursday_alarm_set", true)
            .putString("thursday_alarm_time", String.format("%02d:%02d", hour, minute))
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

        //read melody, type sleep
        //save not null melody
        MelodyStorageManager.saveMelody(this, "Thursday", "sleep", "zen_relax_melody.mp3")
        //read the saved melody
        val melodyPath = MelodyStorageManager.getMelody(this, "Thursday", "sleep")
        //standard if null
        val sleepMelody = melodyPath ?: "rowboat.mp3"

        val intent = Intent(this, AlarmReceiver::class.java).apply {
            putExtra("sleep_melody_file", sleepMelody)
        }
        //pending intent to activate the melody
        val pendingIntent = PendingIntent.getBroadcast(
            this,
            1,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        //get alarmManager for melody
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        // Android permission for alarm
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && !alarmManager.canScheduleExactAlarms()) {
            val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM).apply {
                data = Uri.parse("package:$packageName")
            }
            startActivity(intent)
            Toast.makeText(this, "Permission required for exact sleep alarms", Toast.LENGTH_LONG).show()
            return
        }
        //alarm set to user time
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
    }
    //melody pre wake up
    private fun setPreWakeMelody(wakeHour: Int, wakeMinute: Int) {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, wakeHour)
            set(Calendar.MINUTE, wakeMinute)
            add(Calendar.MINUTE, -15)
            set(Calendar.SECOND, 0)
            if (before(Calendar.getInstance())) add(Calendar.DATE, 1)
        }

        //Read the saved melody for wake up
        //saved melody not null
        MelodyStorageManager.saveMelody(this, "Thursday", "wake", "guitar_relax_melody_wake.mp3")
        //read the saved melody
        val melodyPath = MelodyStorageManager.getMelody(this, "Thursday", "wake")
        //if melody is null
        val wakeMelody = melodyPath ?: "birds_chirping_melody_wake.mp3"
        //get AlarmReceiver intent for melody
        val intent = Intent(this, AlarmReceiver::class.java).apply {
            putExtra("wake_melody_file", wakeMelody)
        }
        //pending intent to activate the melody
        val pendingIntent = PendingIntent.getBroadcast(
            this,
            2,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        //get alarmManager for melody
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        // Android permission for alarm
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && !alarmManager.canScheduleExactAlarms()) {
            val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM).apply {
                data = Uri.parse("package:$packageName")
            }
            startActivity(intent)
            Toast.makeText(this, "Permission required for exact wake alarms", Toast.LENGTH_LONG).show()
            return
        }
        //user time for melody
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
    }


    //fun calculated day
    fun viewThisDay() {
        val today = LocalDate.now()
        val nextThursday = today.with(java.time.temporal.TemporalAdjusters.next(DayOfWeek.THURSDAY))
        //formatter date
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        val formattedDate = nextThursday.format(formatter)

        dayText.text = formattedDate
    }
    //update page layout
    override fun onResume() {
        //super.onResume for the standard state reset
        super.onResume()
        val prefsThursday = getSharedPreferences("alarms", MODE_PRIVATE)
        //read boolean for alarm, standard text is false
        val isAlarmSet = prefsThursday.getBoolean("thursday_alarm_set", false)
        //if the alarm has been set, read the time
        if (isAlarmSet) {
            wakeUpHourTextView.text = prefsThursday.getString("thursday_alarm_time", "--:--")
            sleepUpHourTextView.text = prefsThursday.getString("thursday_sleep_hour", "--:--")
            //and the text for the melody
            alarmMelodyTextView.text = MelodyStorageManager.getMelody(this, "Thursday", "alarm") ?: ""
            sleepMelodyTextView.text = MelodyStorageManager.getMelody(this, "Thursday", "sleep") ?: ""
            wakeUpMelodyTextView.text = MelodyStorageManager.getMelody(this, "Thursday", "wake") ?: ""
        } else {
            // Reset Layout if alarm is not set
            wakeUpHourTextView.text = "--:--"
            sleepUpHourTextView.text = "--:--"
            alarmMelodyTextView.text = ""
            sleepMelodyTextView.text = ""
            wakeUpMelodyTextView.text = ""
        }
    }
}