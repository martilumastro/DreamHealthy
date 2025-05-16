/* While this template provides a good starting point for using Wear Compose, you can always
 * take a look at https://github.com/android/wear-os-samples/tree/main/ComposeStarter to find the
 * most up to date changes to the libraries and their usages.
 */

package com.example.dreamhealthy.wear.presentation


import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.*
import com.example.dreamhealthy.wear.SleepDataGenerator
import com.example.dreamhealthy.wear.SleepSessionData

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                //var session simulation
                var session by remember { mutableStateOf<SleepSessionData?>(null) }

                //layout
                Scaffold {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black)
                            .padding(8.dp),
                        verticalArrangement = Arrangement.Center
                    ) {
                        //Click botton = start the simulation and send the data to the smartphone
                        Button(
                            onClick = {
                                val newSession = SleepDataGenerator.generateSleepSession()
                                SleepDataGenerator.sendSleepData(this@MainActivity, newSession)
                                session = newSession
                                Toast
                                    .makeText(
                                        this@MainActivity,
                                        "Data sent",
                                        Toast.LENGTH_SHORT
                                    )
                                    .show()
                            }
                        ) {
                            Text("Send sleep data", color = Color.White)
                        }

                        Spacer(modifier = Modifier.height(12.dp))
                        //Data visualization
                        session?.let {
                            Text("Total minutes: ${it.totalMinutes} min", color = Color.White)
                            Text("Minutes rest: ${it.minutesRest} min", color = Color.White)
                            Text("Minutes sleep: ${it.minutesSleep} min", color = Color.White)
                            Text("Average bpm: ${it.heartRateAverage}", color = Color.White)
                        } ?: Text("Press to simulate\n", color = Color.White)
                    }
                }
            }
        }
    }
}




