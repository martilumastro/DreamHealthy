package com.example.dreamhealthy

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

class WakeUpMountActivity : AppCompatActivity() {
    private lateinit var buttonBalance: Button
    private lateinit var buttonNature: Button
    private lateinit var buttonMount: Button
    private lateinit var buttonWeather: Button
    private lateinit var buttonMelody: Button
    private lateinit var buttonPlayPause: ImageButton
    private lateinit var buttonNext: ImageButton
    private lateinit var buttonPrevious: ImageButton

    private lateinit var melodyPlayer: MelodyPlayerManager
    private lateinit var imageMelodies: List<ImageView>
    private var lastAnimatedView: View? = null


    //melodies' list
    private val melodies = listOf(
        Melody("city_crickets_wake_up.mp3", R.drawable.img_mountain_sun),
        Melody("strong_rain_melody_wake.mp3", R.drawable.img_mountain_rain),
        Melody("river_water_wake.mp3", R.drawable.img_forest_wake_up),
        Melody("light_wind_melody_wake.mp3", R.drawable.img_hill_sun_white),
        Melody("rain_mountain_melody_wake.mp3", R.drawable.img_mountain_wake_up),
        Melody("strong_wind_melody_wake.mp3", R.drawable.img_mountain_white),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_wake_up_mount)

        melodyPlayer = MelodyPlayerManager(this)
        melodyPlayer.setMelodies(melodies)

        //init
        buttonPlayPause = findViewById(R.id.play_pause_bt)
        buttonNext = findViewById(R.id.next_melody_bt)
        buttonPrevious = findViewById(R.id.previous_melody_bt)

        buttonMelody = findViewById(R.id.go_back_bt)
        buttonNature = findViewById(R.id.musical_nature)
        buttonBalance = findViewById(R.id.musical_meditation)
        buttonMount = findViewById(R.id.musical_mountain)
        buttonWeather = findViewById(R.id.musical_weather)


        //call function
        changePage()
        imageViewMelodies()
        PlayControls()
    }

    fun changePage(){
        buttonMelody.setOnClickListener {
            melodyPlayer.stopMelody()
            val pageMelody = Intent(this, MelodyActivity::class.java)
            startActivity(pageMelody)
        }
        buttonBalance.setOnClickListener {
            melodyPlayer.stopMelody()
            val pageBalance = Intent(this, WakeUpMelodiesActivity::class.java)
            startActivity(pageBalance)
        }
        buttonNature.setOnClickListener {
            melodyPlayer.stopMelody()
            val pageNature = Intent(this, WakeUpNatureActivity::class.java)
            startActivity(pageNature)
        }
        buttonMount.setOnClickListener{
            melodyPlayer.stopMelody()
            val pageMount = Intent(this, WakeUpMountActivity::class.java)
            startActivity(pageMount)
        }
        buttonWeather.setOnClickListener {
            melodyPlayer.stopMelody()
            val pageWeather = Intent(this, WakeUpWeatherActivity::class.java)
            startActivity(pageWeather)
        }
    }

    //fun to melodies
    private fun imageViewMelodies() {
        //init image with melody
        imageMelodies = listOf(
            findViewById(R.id.img_mountain_and_sun),
            findViewById(R.id.img_rain),
            findViewById(R.id.img_forest),
            findViewById(R.id.img_melody_hill),
            findViewById(R.id.img_melody_mountain),
            findViewById(R.id.img_mountain2)
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