package com.example.dreamhealthy

import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AlertDialog
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.dreamhealthy.databinding.ActivityCalendarBinding

class CalendarActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCalendarBinding
    private lateinit var buttonWeekValue: ImageButton
    private lateinit var buttonMyAlarmsClock: ImageButton
    private lateinit var buttonToday: ImageButton
    private lateinit var buttonMenu: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCalendarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // init
        buttonWeekValue = findViewById(R.id.week_values_bt)
        buttonMyAlarmsClock = findViewById(R.id.my_alarms_clock_bt)
        buttonMenu = findViewById(R.id.menu_bt)
        buttonToday = findViewById(R.id.today_bt)


        // call fun
        calendarStructure()
        changePage()
    }

    fun changePage(){
        buttonWeekValue.setOnClickListener {
            val pageWeekValue = Intent (this, WeekValuesMenuActivity::class.java)
            startActivity(pageWeekValue)
        }
        buttonMyAlarmsClock.setOnClickListener {
            val intent = Intent(this, MyAlarmsClockActivity::class.java)
            startActivity(intent)
        }
        buttonMenu.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }
        buttonToday.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }
    //general structure
    fun calendarStructure(){
        binding.calendarView.setOnDateChangeListener {_, year, month, day ->
            val date = ("%02d".format(day) + "-" + "%02d".format((month + 1)) + "-" + year)
            binding.textView.text = date
            showNoteDialog(date)
        }
    }
    //Save a note at a date.
    private fun saveNoteForDate(date: String, note: String) {
        //save in android file calendar_note
        val prefs = getSharedPreferences("calendar_notes", MODE_PRIVATE)
        //change the content
        prefs.edit().putString(date, note).apply()
    }
    //Recover a note saved on a date.
    private fun loadNoteForDate(date: String): String {
        val prefs = getSharedPreferences("calendar_notes", MODE_PRIVATE)
        return prefs.getString(date, "") ?: ""
    }
    //
    //Delete a note for a date.
    private fun deleteNoteForDate(date: String) {
        val prefs = getSharedPreferences("calendar_notes", MODE_PRIVATE)
        prefs.edit().remove(date).apply()
    }

    //fun called when user selects a date on the calendar
    fun showNoteDialog(date: String) {
        //structure pop up
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Event for $date")
        val input = EditText(this)
        //text for utent, hint is a placeholder
        input.hint = "Write a note: "
        input.setText(loadNoteForDate(date))

        builder.setView(input)
        //button save
        builder.setPositiveButton("Save") { dialog, _ ->
            val note = input.text.toString()
            saveNoteForDate(date, note)
            Toast.makeText(this, "Saved event!", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }
        //button cancel
        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.cancel()
        }
        //button delete
        builder.setNeutralButton("Delite") { dialog, _ ->
            deleteNoteForDate(date)
            Toast.makeText(this, "Deleted event!", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }
        //pop up visible
        builder.show()
    }
}