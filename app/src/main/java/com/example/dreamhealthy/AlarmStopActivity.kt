package com.example.dreamhealthy

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class AlarmStopActivity : AppCompatActivity() {
    //var init mediaPlayer null
    private var mediaPlayer: MediaPlayer? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm_stop)
        //melody file and melody type
        val melody = intent.getStringExtra("melody_file") ?: "standard_alarm_melody.mp3"
        val melodyType = intent.getStringExtra("melody_type") ?: "alarm"

        //Layout text for melody type
        val typeText = when (melodyType) {
            "sleep" -> "Started sleep melody"
            "wake" -> "It's time to wake up"
            else -> "Alarm Clock"
        }
        //text view for melody type
        findViewById<TextView>(R.id.alarm_type_tv)?.text = typeText
        //try if melody file is not null, catch if it is null
        try {
            //open melody file
            val afd = assets.openFd(melody)
            mediaPlayer = MediaPlayer().apply {
                setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
                //prepare and start melody
                prepare()
                start()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        //stop button
        findViewById<Button>(R.id.stop_bt).setOnClickListener {
            mediaPlayer?.stop()
            mediaPlayer?.release()
            mediaPlayer = null
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}


