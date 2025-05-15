package com.example.dreamhealthy

import android.Manifest
import android.content.pm.PackageManager
//IMPORT JETPACK COMPOSE https://developer.android.com/develop/ui/compose/setup
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.TimeText

//IMPORT HEALTH SERVICE API https://developer.android.com/reference/androidx/health/services/client/package-summary?hl=en
import android.content.Context
import androidx.health.services.client.HealthServices
import androidx.health.services.client.MeasureClient
import androidx.health.services.client.data.DataType
import androidx.health.services.client.data.Availability
import androidx.health.services.client.data.MeasureCallbackRequest
import androidx.health.services.client.data.Measurement
import androidx.health.services.client.MeasureCallback


//IMPORT NOISES MICROPHONES https://developer.android.com/reference/kotlin/android/media/AudioFormat?hl=en
//https://developer.android.com/reference/kotlin/android/media/AudioRecord?hl=en
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder


//IMPORT MATH
import kotlin.math.log10
import kotlin.math.sqrt
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import android.os.Bundle
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.health.services.client.unregisterMeasureCallback
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class WearMainActivity : ComponentActivity()
{
    private lateinit var measureClient: MeasureClient
    private var heartRateMeasureCallback : MeasureCallback? = null
    private var bodyTemperatureMeasureCallback : MeasureCallback? = null

    private val PERMISSION_REQUEST_BODY_SENSORS = 101
    private val PERMISSION_REQUEST_RECORD_AUDIO = 102

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        measureClient = HealthServices.getClient(this).measureClient

        requestNeededPermissions()
        setContent{
            WearApp()
        }
    }
    private fun requestNeededPermissions() {
        val permissionsToRequest = mutableListOf<String>()
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BODY_SENSORS) != PackageManager.PERMISSION_GRANTED) {
            permissionsToRequest.add(Manifest.permission.BODY_SENSORS)
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            permissionsToRequest.add(Manifest.permission.RECORD_AUDIO)
        }

        if (permissionsToRequest.isNotEmpty()) {
            ActivityCompat.requestPermissions(this, permissionsToRequest.toTypedArray(), PERMISSION_REQUEST_BODY_SENSORS) // Un unico codice, o gestisci separatamente
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
        deviceId: Int
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults, deviceId)
        when (requestCode) {
            PERMISSION_REQUEST_BODY_SENSORS, PERMISSION_REQUEST_RECORD_AUDIO -> {
                if (grantResults.isNotEmpty() && grantResults.all
                    {
                        it == PackageManager.PERMISSION_GRANTED
                    }
                ) {
                    Log.d("PERMISSIONS", "All relevant permissions granted. ")

                } else {
                    Log.w(
                        "PERMISSIONS",
                        "Not all permissions were granted. Some features might not work"
                    )
                }
            }
        }
    }
        @Composable
        fun WearApp() {
            var heartRate by rememberSaveable{ mutableStateOf("-")}
            var bodyTemperature by rememberSaveable{ mutableStateOf("-")}
            var noiseLevel by rememberSaveable{ mutableStateOf("-")}
            val context = LocalContext.current
            val coroutineScope = rememberCoroutineScope()
            Scaffold(timeText = { TimeText() })
            {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("HEART RATE : $heartRate bpm", fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = {
                        if(ActivityCompat.checkSelfPermission(context, Manifest.permission.BODY_SENSORS) == PackageManager.PERMISSION_GRANTED)
                        {
                            heartRate = "Measuring..."
                        measureHeartRate(context) { hr ->
                            heartRate = hr
                        }
                    }
                        else {
                         heartRate = "Sensor permission needed"
                         ActivityCompat.requestPermissions(this@WearMainActivity, arrayOf(Manifest.permission.BODY_SENSORS) , PERMISSION_REQUEST_BODY_SENSORS)

                    }})
                    {
                        Text("Measuring Heart Rate.....")
                    }
                }
                Spacer(modifier = Modifier.height(24.dp)) // Spazio maggiore tra le sezioni

                // Body Temperature Section
                Text("BODY TEMPERATURE: $bodyTemperature °C", fontSize = 16.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = {
                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.BODY_SENSORS) == PackageManager.PERMISSION_GRANTED) {
                        bodyTemperature = "Measuring..."
                        measureBodyTemperature(context) { temp -> bodyTemperature = temp }
                    } else {
                        bodyTemperature = "Sensor permission needed"
                        ActivityCompat.requestPermissions(this@WearMainActivity, arrayOf(Manifest.permission.BODY_SENSORS), PERMISSION_REQUEST_BODY_SENSORS)
                    }
                }) {
                    Text("Measure Body Temp")
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Noise Level Section
                Text("NIGHT NOISES: $noiseLevel dB", fontSize = 16.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = {
                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {
                        noiseLevel = "Measuring..."
                        coroutineScope.launch { // Esegui su un thread di background
                            val result = measureNoiseLevelBlocking(context)
                            withContext(Dispatchers.Main) { // Torna al thread principale per aggiornare l'UI
                                noiseLevel = result
                            }
                        }
                    } else {
                        noiseLevel = "Audio permission needed"
                        ActivityCompat.requestPermissions(this@WearMainActivity, arrayOf(Manifest.permission.RECORD_AUDIO), PERMISSION_REQUEST_RECORD_AUDIO)
                    }
                }) {
                    Text("Measure Night Noises")
                }
            }
        }
        private fun measureHeartRate(context: Context, onResult: (String) -> Unit)
        {

            heartRateMeasureCallback?.let {
                try {
                    measureClient.unregisterMeasureCallback(DataType.HEART_RATE_BPM, it)
                    Log.d("HEALTH_HR", "Previous Heart Rate callback unregistered successfully.")
                } catch (e: Exception) {
                    Log.e("HEALTH_HR", "Error unregistering previous HR callback: ${e.message}")
                }
                heartRateMeasureCallback = null
            }
            heartRateMeasureCallback = object : MeasureCallback {
                override fun onMeasureResult(measurement: Measurement) {
                    if (measurement.dataType == DataType.HEART_RATE_BPM) {
                        val bpm = measurement.value.asDouble()
                        onResult(String.format("%.1f", bpm))
                        Log.d("HEALTH_HR", "Heart Rate: $bpm")
                        // Deregistra dopo aver ottenuto un risultato per una misurazione singola
                        heartRateMeasureCallback?.let {
                            try {
                                measureClient.unregisterMeasureCallback(DataType.HEART_RATE_BPM, it)
                                Log.d(
                                    "HEALTH_HR",
                                    "Heart Rate callback unregistered after measurement."
                                )
                            } catch (e: Exception) {
                                Log.e(
                                    "HEALTH_HR",
                                    "Error unregistering HR callback post-measurement: ${e.message}"
                                )
                            }
                            heartRateMeasureCallback = null
                        }
                    }
                }

                override fun onAvailabilityChanged(dataType: DataType, availability: Availability) {
                    Log.d("HEALTH", "Availability for $dataType changed: $availability")
                    if (availability == Availability.UNAVAILABLE || availability == Availability.ACQUIRING_UNAVAILABLE) {
                        onResult("HR N/A")
                    }
                }

                override fun onMeasureFailure(dataType: DataType, throwable: Throwable) {
                    Log.e(
                        "HEALTH_HR",
                        "Measurement failed for $dataType: ${throwable.message}",
                        throwable
                    )
                    onResult("HR Fail")
                    heartRateMeasureCallback?.let {
                        try {
                            measureClient.unregisterMeasureCallback(DataType.HEART_RATE_BPM, it)
                        } catch (e: Exception) { /* Ignora */
                        }
                        heartRateMeasureCallback = null
                    }
                }
            }
            val request = MeasureCallbackRequest.builder()
                .setDataType(DataType.HEART_RATE_BPM)
                // .setPassiveGoal(false) // Ometti per misurazioni attive on-demand
                .build()

            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.BODY_SENSORS) == PackageManager.PERMISSION_GRANTED) {
                try {
                    measureClient.registerMeasureCallback(request, heartRateMeasureCallback!!)
                    Log.d("HEALTH_HR", "Heart Rate callback registered.")
                } catch (e: Exception) {
                    Log.e("HEALTH_HR", "Error registering HR callback: ${e.message}")
                    onResult("HR Reg Fail")
                }
            } else {
                Log.w("HEALTH_HR", "Body sensors permission not granted for HR.")
                onResult("HR No Perm")
            }
        }


    private fun measureBodyTemperature(context: Context, onResult: (String) -> Unit) {
        bodyTemperatureMeasureCallback?.let {
            try {
                measureClient.unregisterMeasureCallback(DataType.SKIN_TEMPERATURE_CELSIUS, it)
                Log.d("HEALTH_TEMP", "Previous Body Temp callback unregistered.")
            } catch (e: Exception) {
                Log.e("HEALTH_TEMP", "Error unregistering previous Temp callback: ${e.message}")
            }
            bodyTemperatureMeasureCallback = null
        }

        bodyTemperatureMeasureCallback = object : MeasureCallback {
            override fun onMeasureResult(measurement: Measurement) { // Corretto
                if (measurement.dataType == DataType.SKIN_TEMPERATURE_CELSIUS) {
                    val temp = measurement.value.asDouble()
                    onResult(String.format("%.1f", temp))
                    Log.d("HEALTH_TEMP", "Body Temperature: $temp °C")
                    bodyTemperatureMeasureCallback?.let {
                        try {
                            measureClient.unregisterMeasureCallback(DataType.SKIN_TEMPERATURE_CELSIUS, it)
                            Log.d("HEALTH_TEMP", "Body Temp callback unregistered after measurement.")
                        } catch (e: Exception) {
                            Log.e("HEALTH_TEMP", "Error unregistering Temp callback post-measurement: ${e.message}")
                        }
                        bodyTemperatureMeasureCallback = null
                    }
                }
            }

            override fun onAvailabilityChanged(dataType: DataType, availability: Availability) {
                Log.d("HEALTH_TEMP", "Availability for $dataType changed: $availability")
                if (availability == Availability.UNAVAILABLE || availability == Availability.ACQUIRING_UNAVAILABLE) {
                    onResult("Temp N/A")
                }
            }

            override fun onMeasureFailure(dataType: DataType, throwable: Throwable) { // Corretto
                Log.e("HEALTH_TEMP", "Measurement failed for $dataType: ${throwable.message}", throwable)
                onResult("Temp Fail")
                bodyTemperatureMeasureCallback?.let {
                    try {
                        measureClient.unregisterMeasureCallback(DataType.SKIN_TEMPERATURE_CELSIUS, it)
                    } catch (e: Exception) { /* Ignora */ }
                    bodyTemperatureMeasureCallback = null
                }
            }
        }

        val request = MeasureCallbackRequest.builder()
            .setDataType(DataType.SKIN_TEMPERATURE_CELSIUS)
            .build()

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.BODY_SENSORS) == PackageManager.PERMISSION_GRANTED) {
            try {
                measureClient.registerMeasureCallback(request, bodyTemperatureMeasureCallback!!)
                Log.d("HEALTH_TEMP", "Body Temperature callback registered.")
            } catch (e: Exception) {
                Log.e("HEALTH_TEMP", "Error registering Temp callback: ${e.message}")
                onResult("Temp Reg Fail")
            }
        } else {
            Log.w("HEALTH_TEMP", "Body sensors permission not granted for Temperature.")
            onResult("Temp No Perm")
        }
    }


    private suspend fun measureNoiseLevelBlocking(context: Context): String {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            Log.e("NOISE", "RECORD_AUDIO permission not granted.")
            return "Audio Perm Denied"
        }

        val sampleRate = 44100
        val channelConfig = AudioFormat.CHANNEL_IN_MONO
        val audioFormat = AudioFormat.ENCODING_PCM_16BIT
        // getMinBufferSize può restituire valori di errore, gestiscili.
        val minBufferSize = AudioRecord.getMinBufferSize(sampleRate, channelConfig, audioFormat)

        if (minBufferSize == AudioRecord.ERROR_BAD_VALUE || minBufferSize == AudioRecord.ERROR) {
            Log.e("NOISE", "AudioRecord.getMinBufferSize failed. Parameters might be invalid or hardware not supported.")
            return "AudioRecord Param Err"
        }
        // Usa un buffer leggermente più grande del minimo per una lettura più stabile
        val bufferSize = minBufferSize * 2


        var audioRecord: AudioRecord? = null
        return try {
            // La creazione di AudioRecord può fallire se i parametri non sono supportati o per problemi di permessi a basso livello
            audioRecord = AudioRecord(
                MediaRecorder.AudioSource.MIC,
                sampleRate,
                channelConfig,
                audioFormat,
                bufferSize
            )

            if (audioRecord.state != AudioRecord.STATE_INITIALIZED) {
                Log.e("NOISE", "AudioRecord failed to initialize. State: ${audioRecord.state}")
                audioRecord.release() // rilascia se l'inizializzazione fallisce
                return "AudioRecord Init Fail"
            }

            val buffer = ShortArray(bufferSize)
            audioRecord.startRecording()

            // Effettua una singola lettura. Per "night noises" potresti voler campionare per un periodo più lungo.
            // Questa è una lettura bloccante, ma siamo su un dispatcher IO.
            val readResult = audioRecord.read(buffer, 0, buffer.size)
            audioRecord.stop() // Ferma la registrazione prima di rilasciare

            if (readResult <= 0) { // readResult può essere un codice di errore (<0) o 0 se non sono stati letti dati
                Log.e("NOISE", "AudioRecord read failed or returned no data. Result: $readResult")
                return if (readResult < 0) "AudioRead Err: $readResult" else "No Audio Data"
            }

            var sumSquare = 0.0
            for (i in 0 until readResult) { // Usa readResult, non buffer.size, per calcolare la media
                sumSquare += buffer[i].toDouble() * buffer[i].toDouble()
            }

            val meanSquare = sumSquare / readResult
            val rms = sqrt(meanSquare) // Root Mean Square

            // Evita log10(0) che darebbe -Infinity. Un valore RMS molto piccolo è essenzialmente silenzio.
            val decibels = if (rms > 1e-9) 20 * log10(rms) else -120.0 // -120dB come soglia di silenzio

            Log.d("NOISE", "Read $readResult shorts. RMS: $rms, Decibels: $decibels")
            String.format("%.2f", decibels)

        } catch (se: SecurityException) {
            Log.e("NOISE", "SecurityException during AudioRecord operation: ${se.message}", se)
            "Audio Security Err"
        } catch (ise: IllegalStateException) {
            Log.e("NOISE", "IllegalStateException for AudioRecord: ${ise.message}", ise)
            "AudioRecord State Err"
        } catch (e: Exception) {
            Log.e("NOISE", "Generic exception in measureNoiseLevel: ${e.message}", e)
            "Noise Measure Error"
        } finally {
            // Assicurati sempre di rilasciare AudioRecord
            try {
                audioRecord?.release()
            } catch (e: Exception) {
                Log.e("NOISE", "Exception releasing AudioRecord: ${e.message}", e)
            }
        }
    }
    override fun onPause() {
        super.onPause()
        // Deregistra i callback per Health Services quando l'activity non è visibile
        heartRateMeasureCallback?.let {
            try {
                measureClient.unregisterMeasureCallback(DataType.HEART_RATE_BPM, it)
                Log.d("LIFECYCLE", "Heart rate callback unregistered in onPause.")
            } catch (e: Exception) {
                Log.e("LIFECYCLE", "Error unregistering HR callback in onPause: ${e.message}")
            }
            heartRateMeasureCallback = null // Pulisci il riferimento
        }
        bodyTemperatureMeasureCallback?.let {
            try {
                measureClient.unregisterMeasureCallback(DataType.SKIN_TEMPERATURE_CELSIUS, it)
                Log.d("LIFECYCLE", "Body temperature callback unregistered in onPause.")
            } catch (e: Exception) {
                Log.e("LIFECYCLE", "Error unregistering Temp callback in onPause: ${e.message}")
            }
            bodyTemperatureMeasureCallback = null // Pulisci il riferimento
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // È una buona pratica pulire anche qui, sebbene onPause dovrebbe aver già gestito.
        heartRateMeasureCallback?.let {
            try {
                measureClient.unregisterMeasureCallback(DataType.HEART_RATE_BPM, it)
            } catch (e: Exception) { /* Log or ignore */ }
        }
        bodyTemperatureMeasureCallback?.let {
            try {
                measureClient.unregisterMeasureCallback(DataType.SKIN_TEMPERATURE_CELSIUS, it)
            } catch (e: Exception) { /* Log or ignore */ }
        }
    }
}

