package com.example.dreamhealthy

import android.content.Context

object MelodyStorageManager {
    //contex for sharedPreferences access, day, type (alarm, wake, sleep) and file melody
    fun saveMelody(context: Context, day: String, type: String, melodyFilename: String) {
        //get sharedPreferences
        val sharedPreferences = context.getSharedPreferences("MelodiesPrefs", Context.MODE_PRIVATE)
        //save melodyFilename in sharedPreferences with type and day
        sharedPreferences.edit().putString("${type}_$day", melodyFilename).apply()
    }
    //function for read melody
    fun getMelody(context: Context, day: String, type: String): String? {
        val sharedPreferences = context.getSharedPreferences("MelodiesPrefs", Context.MODE_PRIVATE)
        //return string
        return sharedPreferences.getString("${type}_$day", null)
    }
}
