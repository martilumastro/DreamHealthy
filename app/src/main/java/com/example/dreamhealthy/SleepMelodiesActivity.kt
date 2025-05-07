package com.example.dreamhealthy

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SleepMelodiesActivity : AppCompatActivity() {
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


    //init melodies' list
    private val melodies = listOf(
        Melody("zen_background_noise.mp3", R.drawable.img_zen_white),
        Melody("zen_relax_melody.mp3", R.drawable.img_lotus_flower_white),
        Melody("zen_sleep_melody_light.mp3", R.drawable.img_carion_white),
        Melody("rain_zen.mp3", R.drawable.img_zen_rain),
        Melody("zen_gong_1.mp3", R.drawable.img_yin_yang),
        Melody("dreams_yoga.mp3", R.drawable.img_moon_hands),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sleep_melodies)

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
    fun buttonChange()
    {
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

    //init image with melody
    private fun imageViewMelodies() {
        imageMelodies = listOf(
            findViewById(R.id.img_zen),
            findViewById(R.id.img_lotus),
            findViewById(R.id.img_carion),
            findViewById(R.id.img_melody_rain),
            findViewById(R.id.img_melody_yin_yang),
            findViewById(R.id.img_moon_hands)
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