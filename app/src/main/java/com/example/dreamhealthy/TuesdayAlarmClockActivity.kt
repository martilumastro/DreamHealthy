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
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
class TuesdayAlarmClockActivity : AppCompatActivity() {

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

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tuesday_alarm_clock)

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
                        val prefsTuesday = getSharedPreferences("alarms", MODE_PRIVATE).edit()
                        prefsTuesday.putBoolean("tuesday_alarm_set", true)
                        prefsTuesday.putString("tuesday_alarm_time", String.format("%02d:%02d", selectedHour, selectedMinute))
                        prefsTuesday.putString("tuesday_sleep_hour", String.format("%02d:%02d", sleepHour, sleepMinute))
                        prefsTuesday.putString("tuesday_alarm_melody", alarmMelodyTextView.text.toString())
                        prefsTuesday.putString("tuesday_sleep_melody", sleepMelodyTextView.text.toString())
                        prefsTuesday.putString("tuesday_wake_melody", wakeUpMelodyTextView.text.toString())
                        prefsTuesday.apply()
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
                        val prefsTuesdayDelete = getSharedPreferences("alarms", MODE_PRIVATE)
                        prefsTuesdayDelete.edit()
                            .putBoolean("tuesday_alarm_set", false)
                            .remove("tuesday_alarm_time")
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
    //fun for alarm
    private fun setAlarm(hour: Int, minute: Int) {
        //calendar object with hour and minute chosen by the user
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)

            //Next tuesday if today is not tuesday
            while (get(Calendar.DAY_OF_WEEK) != Calendar.TUESDAY) {
                add(Calendar.DATE, 1)
            }
            //Next tuesday if today is tuesday and time is before current time
            if (before(Calendar.getInstance())) {
                add(Calendar.WEEK_OF_YEAR, 1)
            }
        }

        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        // check permission with alarmReceiver for music SCHEDULE_EXACT_ALARM
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && !alarmManager.canScheduleExactAlarms()) {
            val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM).apply {
                data = Uri.parse("package:$packageName")
            }
            startActivity(intent)
            Toast.makeText(this, "You give permission to alarms", Toast.LENGTH_LONG).show()
            return
        }
        //Read the saved melody for alarm
        //first save melody for thursday
        MelodyStorageManager.saveMelody(this, "Tuesday", "alarm", "music_alarm_melody1.mp3")
        //read name of the melody
        val melodyPath = MelodyStorageManager.getMelody(this, "Tuesday", "alarm")
        //standard if melody is null
        val alarmMelody = melodyPath ?: "standard_alarm_melody.mp3"

        //connection with alarmReceiver for music
        val intent = Intent(this, AlarmReceiver::class.java).apply {
            putExtra("standard alarm", alarmMelody )
        }

        //Creating a PendingIntent upgradeable
        val pendingIntent = PendingIntent.getBroadcast(
            this,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        //if user click on the notification open app
        val showIntent = Intent(this, TuesdayAlarmClockActivity::class.java)
        val showPendingIntent = PendingIntent.getActivity(
            this,
            1,
            showIntent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        //set alarm with alarmManager
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
        // Save on SharedPreferences ("alarms")
        //save state and clock of the alarm for toggle button in my alarms clock
        val prefsTuesday = getSharedPreferences("alarms", MODE_PRIVATE)
        prefsTuesday.edit()
            .putBoolean("tuesday_alarm_set", true)
            .putString("tuesday_alarm_time", String.format("%02d:%02d", hour, minute))
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
        MelodyStorageManager.saveMelody(this, "Tuesday", "sleep", "zen_relax_melody.mp3")
        //read the saved melody
        val melodyPath = MelodyStorageManager.getMelody(this, "Tuesday", "sleep")
        //standard if null
        val sleepMelody = melodyPath ?: "rowboat.mp3"

        val intent = Intent(this, AlarmReceiver::class.java).apply {
            putExtra("standard alarm", sleepMelody)
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
    //fun for pre wake up melody
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
        MelodyStorageManager.saveMelody(this, "Friday", "wake", "guitar_relax_melody_wake.mp3")
        //read the saved melody
        val melodyPath = MelodyStorageManager.getMelody(this, "Friday", "wake")
        //if melody is null
        val wakeMelody = melodyPath ?: "birds_chirping_melody_wake.mp3"
        //get AlarmReceiver intent for melody
        val intent = Intent(this, AlarmReceiver::class.java).apply {
            putExtra("standard alarm", wakeMelody)
        }
        //pending intent to activate the melod
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
        val nextTuesday = today.with(java.time.temporal.TemporalAdjusters.next(DayOfWeek.TUESDAY))
        //formatted
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        val formattedDate = nextTuesday.format(formatter)

        dayText.text = formattedDate
    }

    //update page layout
    override fun onResume() {
        //super.onResume for the standard state reset
        super.onResume()

        val prefsTuesday = getSharedPreferences("alarms", MODE_PRIVATE)
        //read boolean for alarm, standard text is false
        val isAlarmSet = prefsTuesday.getBoolean("tuesday_alarm_set", false)
        //if the alarm has been set, read the time
        if (isAlarmSet) {
            wakeUpHourTextView.text = prefsTuesday.getString("tuesday_alarm_time", "--:--")
            sleepUpHourTextView.text = prefsTuesday.getString("tuesday_sleep_hour", "--:--")
            //and the text for the melody
            alarmMelodyTextView.text = MelodyStorageManager.getMelody(this, "Tuesday", "alarm") ?: ""
            sleepMelodyTextView.text = MelodyStorageManager.getMelody(this, "Tuesday", "sleep") ?: ""
            wakeUpMelodyTextView.text = MelodyStorageManager.getMelody(this, "Tuesday", "wake") ?: ""
        } else {
            // Reset layout id there isn't alarm
            wakeUpHourTextView.text = "--:--"
            sleepUpHourTextView.text = "--:--"
            alarmMelodyTextView.text = ""
            sleepMelodyTextView.text = ""
            wakeUpMelodyTextView.text = ""
        }
    }
}