package com.example.dreamhealthy.wear

import android.content.Context
import com.google.android.gms.wearable.Wearable
import com.google.android.gms.wearable.*
import android.util.Log

//Kotlin singleton, single object that contains two functions:
object SleepDataGenerator {
    //second function = sending simulated data to the smartphone
    fun sendSimulatedMetrics(context: Context , heart: Float, temp: Float, noise: Float, time: Float, sessionStart: Long , sessionEnd : Long, day : String) {
        // Create a DataClient instance to communicate
        val dataClient : DataClient = Wearable.getDataClient(context)
        val request = PutDataMapRequest.create("/simulated_metrics").apply {
            dataMap.putFloat("heart rate" , heart)
            dataMap.putFloat("temperature", temp)
            dataMap.putFloat("noise", noise)
            dataMap.putFloat("time", time)
            dataMap.putLong("session_start",sessionStart)
            dataMap.putLong("session_end",sessionEnd)
            dataMap.putString("day" , day)
        }.asPutDataRequest().setUrgent()
        //sending data
        dataClient.putDataItem(request)
            .addOnSuccessListener {
                Log.d("Wear", "Sleep data sent successfully : $heart , $temp , $noise")
            }
            .addOnFailureListener {
                Log.e("Wear", "Failed to send sleep data", it)
            }

    }

}

