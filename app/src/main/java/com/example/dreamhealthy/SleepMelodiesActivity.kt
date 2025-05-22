package com.example.dreamhealthy

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
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
            //click image, start melody
            imageView.setOnClickListener {
                animationImgSelection(it)

                melodyPlayer.playMelody(index)
                buttonPlayPause.setImageResource(R.drawable.icon_pause_music_black)
            }
            //long click on image, choose the day
            imageView.setOnLongClickListener {
                showDaySelectionDialog(melodies[index])
                true
            }
        }
    }
    //pop up choose the day
    //input object melody
    fun showDaySelectionDialog(selectedMelody: Melody) {
        //array days of the week
        val days = arrayOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Choose the day for the alarm!")
        //list of days with which
        builder.setItems(days) { _, which ->
            //select a day
            val selectedDay = days[which]
            //save melody
            MelodyStorageManager.saveMelody(this, selectedDay, "sleep", selectedMelody.audioPath)
            //notification assigned melody for the day
            Toast.makeText(this, "Melody assigned to $selectedDay", Toast.LENGTH_SHORT).show()
            goToDayActivity(selectedDay)
        }
        builder.show()
    }
    //return to the daily alarm pages
    fun goToDayActivity(day: String) {
        val intent = when (day) {
            "Monday" -> Intent(this, MondayAlarmClockActivity::class.java)
            "Tuesday" -> Intent(this, TuesdayAlarmClockActivity::class.java)
            "Wednesday" -> Intent(this, WednesdayAlarmClockActivity::class.java)
            "Thursday" -> Intent(this, ThursdayAlarmClockActivity::class.java)
            "Friday" -> Intent(this, FridayAlarmClockActivity::class.java)
            "Saturday" -> Intent(this, SaturdayAlarmClockActivity::class.java)
            "Sunday" -> Intent(this, SundayAlarmClockActivity::class.java)
            else -> null
        }
        //start activity if intent is not null
        intent?.let { startActivity(it) }
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