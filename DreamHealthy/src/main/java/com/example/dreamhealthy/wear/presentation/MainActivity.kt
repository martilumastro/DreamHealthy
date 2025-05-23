
package com.example.dreamhealthy.wear.presentation
import android.content.Context
import android.os.Bundle
import android.text.format.DateUtils.formatElapsedTime
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
import kotlinx.coroutines.*
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
class MainActivity : ComponentActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme{
                var sessionActive by remember {mutableStateOf(false)}
                var startTime by remember { mutableStateOf(0L) }
                var sessionEndTime by remember { mutableStateOf(0L) }
                var elapsedTime by remember { mutableStateOf(0L) }

              /*  var heartRate by remember { mutableStateOf("-") }
                var bodyTemp by remember { mutableStateOf("-") }
                var noiseLevel by remember { mutableStateOf("-")}*/

                var coroutineScope = rememberCoroutineScope()
                val context = LocalContext.current
                var simulationJob by remember { mutableStateOf<Job?>(null) }

                Scaffold {
                    Column(
                        modifier = Modifier.fillMaxSize().background(Color.Black).padding(8.dp),
                        verticalArrangement = Arrangement.Center
                    ) {
                        if(!sessionActive)
                        {
                            Button(onClick = {
                                sessionActive = true
                                startTime = System.currentTimeMillis()
                                sessionEndTime = 0L
                                Toast.makeText(context, "Start Session" , Toast.LENGTH_SHORT).show()

                                simulationJob = coroutineScope.launch {
                                    while(isActive)
                                    {
                                        val hr = Random.nextInt(55,90).toFloat()
                                        val temp = (360..375).random() /10f
                                        val noise = (25..55).random().toFloat()
                                        val currentTime = getCurrentFloatTime()
                                        val day = getCurrentDay()

                                        saveSimulateData(context,hr,temp,noise,currentTime,day,startTime)

                                        SleepDataGenerator.sendSimulatedMetrics(
                                            context = context,
                                            heart = hr,
                                            temp = temp,
                                            noise = noise,
                                            time = currentTime,
                                            sessionStart = startTime,
                                            sessionEnd = sessionEndTime,
                                            day = day
                                        )
                                        delay(5_000L)
                                    }
                                }
                                coroutineScope.launch {
                                    while(sessionActive && isActive){
                                        elapsedTime = System.currentTimeMillis() - startTime //so we know the time
                                        delay(1000L) //UPDATE EVERY SEC
                                    }
                                }
                            }) {
                                Text("Start Simulation" , color = Color.White)
                            }
                        } else {
                            Button(onClick =  {
                                sessionActive = false
                                simulationJob?.cancel()
                                sessionEndTime = System.currentTimeMillis()

                                SleepDataGenerator.sendSimulatedMetrics(
                                    context = context,
                                    heart = -1f, // close session
                                    temp = -1f,
                                    noise = -1f,
                                    time = getCurrentFloatTime(),
                                    sessionStart = startTime,
                                    sessionEnd = sessionEndTime,
                                    day = getCurrentDay()
                                )
                                Toast.makeText(context, "Session Ended at : ${formatTime(sessionEndTime)}", Toast.LENGTH_LONG).show()

                            })
                            {
                              Text("End Simulation", color = Color.Red)
                            }
                        }
                        Spacer(modifier = Modifier.height(12.dp))

                        if(sessionActive || sessionEndTime !=0L)
                            {
                            Text("Session started at: ${formatTime(startTime)}", color = Color.White)
                            if(sessionActive)
                            {
                                Text(
                                    "Elapsed Time : ${formatElapsedTime(elapsedTime)}",
                                    color = Color.White
                                )
                            }
                                else if(sessionEndTime != 0L)
                                {
                                    Text("Session ended at: ${formatTime(sessionEndTime)}", color = Color.White)

                                }

                            }
                        }
                    }
                }
            }
        }


    private fun saveSimulateData(context: Context , heart : Float , temp : Float , noise : Float, time : Float, day: String, sessionStartTime : Long)
    {

        val sharedPrefs = context.getSharedPreferences("sim_data", Context.MODE_PRIVATE)
        val existingJson = sharedPrefs.getString("sim_data_list",null)
        val jsonArray = if(existingJson != null) JSONArray(existingJson) else JSONArray()

        val sessionData = JSONObject().apply {
            put("heart" , heart)
            put("temp",temp)
            put("noise",noise)
            put("time",time)
            put("start_time_point" , System.currentTimeMillis())  // Save start time of session
            put("session_start_global",sessionStartTime) // Placeholder end time , update after session
            put("day", day)
        }
        jsonArray.put(sessionData)
        sharedPrefs.edit().putString("sim_data_list" , jsonArray.toString()).apply()
        Log.d("SaveData", "Saved data point : $sessionData")
    }

    private fun formatTime(timestamp : Long): String{
        if(timestamp == 0L)
            return "N/A"
        val sdf = SimpleDateFormat("HH:mm:ss",Locale.getDefault())
        return sdf.format(Date(timestamp))
    }

    private fun formatElapsedTime(milliseconds: Long) : String {
         val totalSeconds = milliseconds / 1000
         val minutes = totalSeconds / 60
         val seconds = totalSeconds % 60
        return String.format("%02d:%02d", minutes, seconds)
    }

    private fun getCurrentFloatTime(): Float {
        val now = java.util.Calendar.getInstance()
        val hour = now.get(java.util.Calendar.HOUR_OF_DAY)
        val minute = now.get(java.util.Calendar.MINUTE)
        return hour +(minute /60f)
    }
    fun getCurrentDay(): String {
        val sdf = SimpleDateFormat("EEEE", Locale.getDefault())
        return sdf.format(Date()) // es. "Sunday"
    }
}

/* @Composable
    fun SimulatedMetricsDisplay(){

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

            }
        }

        Column {
            Text("Heart Rate : $heartRate bpm", color = Color.Red)
            Text("Body Temperature :  $bodyTemp °C", color = Color.Yellow)
            Text("Noise Level : $noiseLevel dB", color = Color.Black)
        }
    }*/




