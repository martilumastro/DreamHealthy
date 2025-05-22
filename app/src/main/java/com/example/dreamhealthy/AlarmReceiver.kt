package com.example.dreamhealthy

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        //For reading all types of melodies
        val alarmMelody = intent.getStringExtra("alarm_melody_file")
        val sleepMelody = intent.getStringExtra("sleep_melody_file")
        val wakeMelody = intent.getStringExtra("wake_melody_file")

        // ?: (Elvis operator) to selection, customer melody or standard
        val selectedMelody = alarmMelody ?: sleepMelody ?: wakeMelody ?: "standard_alarm_melody.mp3"

        //reproduced type of melody, if it isn't null
        val melodyType = when {
            alarmMelody != null -> "alarm"
            sleepMelody != null -> "sleep"
            wakeMelody != null -> "wake"
            else -> "alarm"
        }

        //Intent for open AlarmStopActivity
        val alarmIntent = Intent(context, AlarmStopActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            putExtra("melody_file", selectedMelody)
            putExtra("melody_type", melodyType)
        }
        //start AlarmStopActivity
        context.startActivity(alarmIntent)
    }
}

