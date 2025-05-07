package com.example.dreamhealthy

import android.media.MediaPlayer
import android.content.res.AssetFileDescriptor
import android.content.Context
import android.util.Log


class MelodyPlayerManager(
    private val context: Context) {

    private var isPlaying = false
    private var currentIndex = 0
    private var mediaPlayer: MediaPlayer? = null
    private var melodies: List<Melody> = emptyList()

    fun setMelodies(list: List<Melody>){
        melodies = list
    }

    //index and play melody
    fun playMelody(index: Int){
        if (index !in melodies.indices) return
        stopMelody()

        val melody = melodies[index]
        val afd: AssetFileDescriptor = context.assets.openFd(melody.audioPath)

        mediaPlayer = MediaPlayer().apply{
            setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
            prepare()
            start()
            }
        isPlaying = true
        currentIndex = index

        }


    //pause or resume playback
    fun togglePlayPause(){
        mediaPlayer?.let{
            if(isPlaying){
                it.pause()
                isPlaying = false
            }else{
                it.start()
                isPlaying = true
            }
        } ?: run {
            playMelody(currentIndex)
        }
    }

    //stop and reset melody
    fun stopMelody(){
        //Log.d("MelodyPlayerManager", "stopMelody called")
        mediaPlayer?.let{
            if(it.isPlaying){
                //Log.d("MelodyPlayerManager", "Pausing and resetting media player")
                it.pause()
                it.seekTo(0)
            }
            it.release()
        }
        mediaPlayer = null
        isPlaying = false
    }

    fun nextMelody(){
        if (melodies.isNotEmpty()) {
            val newIndex = (currentIndex + 1) % melodies.size
            playMelody(newIndex)
        }
    }

    fun previousMelody() {
        if (melodies.isNotEmpty()) {
            val newIndex = (currentIndex - 1 + melodies.size) % melodies.size
            playMelody(newIndex)
        }
    }

    fun isPlaying(): Boolean = isPlaying

    fun release(){
        mediaPlayer?.release()
        mediaPlayer
    }

}