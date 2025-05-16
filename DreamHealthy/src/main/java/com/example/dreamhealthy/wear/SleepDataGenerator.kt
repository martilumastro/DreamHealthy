package com.example.dreamhealthy.wear

import android.content.Context
import com.google.android.gms.wearable.Wearable
import kotlin.random.Random
import com.google.android.gms.wearable.*
import android.util.Log
//class session with all the data
data class SleepSessionData(
    val totalMinutes: Int,
    val minutesRest: Int,
    val minutesSleep: Int,
    val heartRateList: List<Int>,
    val heartRateAverage: Int
)
//Kotlin singleton, single object that contains two functions:
object SleepDataGenerator {
    //first function random data generation
    fun generateSleepSession(): SleepSessionData {
        val totalMinutes = Random.nextInt(360, 661)  // 6h to 11h
        val minutesRest = Random.nextInt(10, 61)
        val minutesSleep = totalMinutes - minutesRest

        val heartRateList = mutableListOf<Int>()

        // Heartrate during the rest
        repeat(minutesRest) {
            heartRateList.add(Random.nextInt(60, 101))
        }

        // Heartrate during the sleep
        repeat(minutesSleep) {
            heartRateList.add(Random.nextInt(35, 61))
        }
        //average
        val heartRateAverage = heartRateList.sum() / heartRateList.size

        return SleepSessionData(
            totalMinutes,
            minutesRest,
            minutesSleep,
            heartRateList,
            heartRateAverage
        )
    }
    //second function = sending simulated data to the smartphone
    fun sendSleepData(context: Context, session: SleepSessionData) {
        // Create a DataClient instance to communicate
        val dataClient = Wearable.getDataClient(context)
        //session's data
        val dataMap = DataMap().apply {
            putInt("totalMinutes", session.totalMinutes)
            putInt("minutesRest", session.minutesRest)
            putInt("minutesSleep", session.minutesSleep)
            putInt("heartRateAverage", session.heartRateAverage)
            putIntegerArrayList("heartRateList", ArrayList(session.heartRateList))
        }
        //update dataMap
        val request = PutDataMapRequest.create("/sleep-session").apply {
            dataMap.putLong("timestamp", System.currentTimeMillis())
            dataMap.putAll(dataMap)
        }.asPutDataRequest().setUrgent()
        //sending data
        dataClient.putDataItem(request)
            .addOnSuccessListener {
                Log.d("Wear", "Sleep data sent successfully")
            }
            .addOnFailureListener {
                Log.e("Wear", "Failed to send sleep data", it)
            }
    }
}