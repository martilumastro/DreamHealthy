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

class WakeUpWeatherActivity : AppCompatActivity() {
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
        Melody("w_raining_glass_melody_wake.mp3", R.drawable.img_sun_clouds),
        Melody("w_rain_and_thunder_wake.mp3", R.drawable.img_rain_wake_up),
        Melody("w_rain_door_melody_wake.mp3", R.drawable.img_wind_wake_up),
        Melody("w_thunder_melody_wake.mp3", R.drawable.img_storm_rain),
        Melody("weather_thunder_city_melody_wake.mp3", R.drawable.img_rain_wake_up2),
        Melody("weather_thunder_light_melody_wake.mp3", R.drawable.img_snow_wake_up),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_wake_up_weather)

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
            findViewById(R.id.img_sun),
            findViewById(R.id.img_rain_light),
            findViewById(R.id.img_wind),
            findViewById(R.id.img_storm),
            findViewById(R.id.img_rain_strong),
            findViewById(R.id.img_snow)
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