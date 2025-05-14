package com.example.dreamhealthy

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.util.Log

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val standardAlarm = intent.getStringExtra("standard alarm") ?: "standard_alarm_melody.mp3"

        // Visible activity
        val alarmIntent = Intent(context, AlarmStopActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            putExtra("standard alarm", standardAlarm)
        }
        context.startActivity(alarmIntent)
    }
}
