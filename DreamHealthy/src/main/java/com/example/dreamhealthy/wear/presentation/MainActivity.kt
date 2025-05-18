
package com.example.dreamhealthy.wear.presentation


import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity

import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.*
import com.example.dreamhealthy.wear.SleepDataGenerator
import kotlin.random.Random

import kotlinx.coroutines.delay
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
//import androidx.core.app.ComponentActivity




class MainActivity : ComponentActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContent {
            SimulatedMetricsDisplay()
        }
    }

    fun getCurrentDay(): String {
        val sdf = SimpleDateFormat("EEEE", Locale.getDefault())
        return sdf.format(Date()) // es. "Sunday"
    }

    @Composable
    fun SimulatedMetricsDisplay(){
        var session by remember {mutableStateOf(null)}
        var heartRate by remember { mutableStateOf("-") }
        var bodyTemp by remember { mutableStateOf("-") }
        var noiseLevel by remember { mutableStateOf("-")}
        val simulatedData = remember { mutableStateListOf<Triple<Float, Float, Float>>() }
        val context = LocalContext.current
        LaunchedEffect(Unit) {
            Log.d("SimulatedMetrics","LaunchedEffect started.")
            var iteration = 0
            while(true)
            {
                iteration++
                Log.d("SimulatedMetrics","Iteration : $iteration - Genereting data......")
                val hr = Random.nextInt(55,90).toFloat()
                val temp = (360..375).random() /10f
                val noise = (25..55).random().toFloat()

                heartRate = hr.toInt().toString() // convert into to String
                bodyTemp = "%.1f".format(temp)
                noiseLevel = "%.1f".format(noise)
                val timeNow = getCurrentFloatTime()

                simulatedData.add(Triple(timeNow,hr,temp))
                Log.d("SimulatedMetrics","Data : HR=$heartRate , Temp = $bodyTemp , Noise = $noiseLevel , Time = $timeNow")
                try {
                   Log.i("SimulatedMetrics", "Iteration : $iteration - Attempting to send metrics.....")

                    val sharedPrefs = context.getSharedPreferences("sim_data", android.content.Context.MODE_PRIVATE)
                    val existingJson = sharedPrefs.getString("sim_data_list",null)
                    val jsonArray = if(existingJson != null) JSONArray(existingJson) else JSONArray()

                    val obj = JSONObject()
                    obj.put("heart",hr)
                    obj.put("temp",temp)
                    obj.put("noise",noise)
                    obj.put("time",timeNow)
                    obj.put("day",getCurrentDay())

                    jsonArray.put(obj)

                    sharedPrefs.edit().putString("sim_data_list",jsonArray.toString()).apply()
                    Log.i("SimulatedMetrics", "Iteration : $iteration - Send Simulated Metrics call completed (sembra senza eccezioni qui")
                }  catch (e: Exception)
                {
                    Log.e("SimulatedMetrics", "Iteration: $iteration - EXCEPTION during/after sendSimulatedMetrics call!", e)

                }
                Log.e("SimulatedMetrics","Iteration : $iteration - Delaying for 5 seconds......")
                delay(5_000L)
            }
        }

        Column {
            Text("Heart Rate : $heartRate bpm", color = Color.Red)
            Text("Body Temperature :  $bodyTemp °C", color = Color.Yellow)
            Text("Noise Level : $noiseLevel dB", color = Color.Black)
        }
    }
    private fun getCurrentFloatTime(): Float {
        val now = java.util.Calendar.getInstance()
        val hour = now.get(java.util.Calendar.HOUR_OF_DAY)
        val minute = now.get(java.util.Calendar.MINUTE)
        return hour +(minute /60f)
    }
}




