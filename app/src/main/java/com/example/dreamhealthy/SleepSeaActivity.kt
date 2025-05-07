package com.example.dreamhealthy

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.dreamhealthy.Melody


class SleepSeaActivity : AppCompatActivity() {
    //var
    private lateinit var buttonBalance: Button
    private lateinit var buttonSea: Button
    private lateinit var buttonForest: Button
    private lateinit var buttonDreams: Button
    private lateinit var buttonMelody: Button
    private lateinit var buttonPlayPause: ImageButton
    private lateinit var buttonNext: ImageButton
    private lateinit var buttonPrevious: ImageButton

    private lateinit var melodyPlayer: MelodyPlayerManager
    private lateinit var imageMelodies: List<ImageView>
    private var lastAnimatedView: View? = null


    //melodies' list
    private val melodies = listOf(
        Melody("rowboat.mp3", R.drawable.img_rowboat_white),
        Melody("sea_swimming.mp3", R.drawable.img_sea_swimming_white),
        Melody("sea_waves.mp3", R.drawable.img_waves_white),
        Melody("sea_waves_light_melody.mp3", R.drawable.img_sea_waves_light_white),
        Melody("sea_waves_with_birds.mp3", R.drawable.sea_waves_with_birds),
        Melody("strong_wind_sea.mp3", R.drawable.img_strong_wind_white),
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sleep_sea)

        melodyPlayer = MelodyPlayerManager(this)
        melodyPlayer.setMelodies(melodies)

        //init
        buttonPlayPause = findViewById(R.id.play_pause_bt)
        buttonNext = findViewById(R.id.next_melody_bt)
        buttonPrevious = findViewById(R.id.previous_melody_bt)

        buttonMelody = findViewById(R.id.go_back_bt)
        buttonSea = findViewById(R.id.musical_sea)
        buttonForest = findViewById(R.id.musical_nature)
        buttonDreams = findViewById(R.id.musical_night)
        buttonBalance = findViewById(R.id.musical_meditation)


        //call function
        buttonChange()
        imageViewMelodies()
        PlayControls()
    }


    //fun for changing page (navbar)
    fun buttonChange() {
        // button myAlarmsClockBt  --> return to myAlarmsClock's page
        buttonMelody.setOnClickListener {
            melodyPlayer.stopMelody()
            val pageMelody = Intent(this, MelodyActivity::class.java)
            startActivity(pageMelody)
        }
        buttonBalance.setOnClickListener {
            melodyPlayer.stopMelody()
            val pageBalance = Intent(this, SleepMelodiesActivity::class.java)
            startActivity(pageBalance)
        }
        buttonSea.setOnClickListener {
            melodyPlayer.stopMelody()
            val pageSea = Intent(this, SleepSeaActivity::class.java)
            startActivity(pageSea)
        }
        buttonForest.setOnClickListener {
            melodyPlayer.stopMelody()
            val pageForest = Intent(this, SleepForestActivity::class.java)
            startActivity(pageForest)
        }
        buttonDreams.setOnClickListener {
            melodyPlayer.stopMelody()
            val pageDreams = Intent(this, SleepDreamsActivity::class.java)
            startActivity(pageDreams)
        }
    }


    //fun to melodies
    private fun imageViewMelodies() {
        //init image with melody
        imageMelodies = listOf(
            findViewById(R.id.img_melody_rowboat),
            findViewById(R.id.img_melody_swimming),
            findViewById(R.id.img_melody_waves_strong),
            findViewById(R.id.img_melody_light_waves),
            findViewById(R.id.img_melody_birds),
            findViewById(R.id.img_strong_wind)
        )

        // imageview click
        imageMelodies.forEachIndexed { index, imageView ->
            imageView.setOnClickListener {
               animationImgSelection(it)

               melodyPlayer.playMelody(index)
               buttonPlayPause.setImageResource(R.drawable.icon_pause_music_black)
             }

        }

    }


//fun to melodies
private fun PlayControls(){
    buttonPlayPause.setOnClickListener {
        melodyPlayer.togglePlayPause()
        val icon = if (melodyPlayer.isPlaying()) {
            R.drawable.icon_pause_music_black
        } else {
            R.drawable.icon_play_music_black
        }
        buttonPlayPause.setImageResource(icon)
    }
    buttonNext.setOnClickListener {
        melodyPlayer.nextMelody()
    }
    buttonPrevious.setOnClickListener {
        melodyPlayer.previousMelody()
    }
}


//animation
private fun animationImgSelection(view: View) {
    lastAnimatedView?.animate()
        ?.scaleX(1f)
        ?.scaleY(1f)
        ?.setDuration(150)
        ?.start()
    //Animation current image
    view.animate()
        .scaleX(1.1f)
        .scaleY(1.1f)
        .setDuration(150)
        .start()
    lastAnimatedView = view
}


//for text mediaPlayer init
    override fun onDestroy(){
        melodyPlayer.release()
        super.onDestroy()
    }

    override fun onPause() {
        melodyPlayer.stopMelody()
        super.onPause()
    }
}



