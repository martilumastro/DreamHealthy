package com.example.dreamhealthy.wear

import android.content.Context
import com.google.android.gms.wearable.Wearable
import kotlin.random.Random
import com.google.android.gms.wearable.*
import android.util.Log

//Kotlin singleton, single object that contains two functions:
object SleepDataGenerator {
    //second function = sending simulated data to the smartphone
    fun sendSimulatedMetrics(context: Context , heart: Float, temp: Float, noise: Float, time: Float) {
        // Create a DataClient instance to communicate
        val dataClient : DataClient = Wearable.getDataClient(context)
        val request = PutDataMapRequest.create("/simulated_metrics").apply {
            dataMap.putFloat("heart rate" , heart)
            dataMap.putFloat("temperature", temp)
            dataMap.putFloat("noise", noise)
            dataMap.putFloat("time", time)
            dataMap.putLong("timestamp", System.currentTimeMillis())
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

