package com.example.dreamhealthy

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.app.AlertDialog
import android.content.Context
import android.widget.Toast

class AlarmMelodiesActivity : AppCompatActivity() {
    private lateinit var buttonStandard: Button
    private lateinit var buttonNatural: Button
    private lateinit var buttonMelody: Button

    private lateinit var buttonPlayPause: ImageButton
    private lateinit var buttonNext: ImageButton
    private lateinit var buttonPrevious: ImageButton

    private lateinit var melodyPlayer: MelodyPlayerManager
    private lateinit var imageMelodies: List<ImageView>
    private var lastAnimatedView: View? = null


    //init melodies' list
    private val melodies = listOf(
        Melody("standard_alarm_melody.mp3", R.drawable.img_alarm_standard),
        Melody("music_alarm_melody1.mp3", R.drawable.img_alarm_melody),
        Melody("music_alarm_melody2.mp3", R.drawable.img_alarm_melody2),
        Melody("music_alarm_melody3.mp3", R.drawable.img_alarm_melody3),
        Melody("night_alarm_melody.mp3", R.drawable.img_night_alarm),
        Melody("piano_alarm_melody.mp3", R.drawable.img_piano_melody),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm_melodies)

        melodyPlayer = MelodyPlayerManager(this)
        melodyPlayer.setMelodies(melodies)

        //init
        buttonPlayPause = findViewById(R.id.play_pause_bt)
        buttonNext = findViewById(R.id.next_melody_bt)
        buttonPrevious = findViewById(R.id.previous_melody_bt)

        buttonStandard = findViewById(R.id.musical_standard)
        buttonNatural = findViewById(R.id.musical_natural)
        buttonMelody = findViewById(R.id.go_back_bt)

        //call function
        changePage()
        imageViewMelodies()
        PlayControls()
    }
    //change the page
    fun changePage(){
        buttonMelody.setOnClickListener {
            melodyPlayer.stopMelody()
            val pageMelody = Intent(this, MelodyActivity::class.java)
            startActivity(pageMelody)
        }
        buttonStandard.setOnClickListener {
            melodyPlayer.stopMelody()
            val pageStandard = Intent(this, AlarmMelodiesActivity::class.java)
            startActivity(pageStandard)
        }
        buttonNatural.setOnClickListener {
            melodyPlayer.stopMelody()
            val pageNatural = Intent(this, AlarmNaturalActivity::class.java)
            startActivity(pageNatural)
        }
    }

    //init image with melody
    fun imageViewMelodies() {
        imageMelodies = listOf(
            findViewById(R.id.img_standard),
            findViewById(R.id.img_melody1),
            findViewById(R.id.img_melody2),
            findViewById(R.id.img_melody3),
            findViewById(R.id.img_night),
            findViewById(R.id.img_piano)
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
            MelodyStorageManager.saveMelody(this, selectedDay, "alarm", selectedMelody.audioPath)
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
    fun PlayControls(){
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
    fun animationImgSelection(view: View) {
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