package com.example.sampleprojectsetup.utilities.helper

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.os.AsyncTask

class PlayAudioManager {
    companion object{
        private var mediaPlayer: MediaPlayer? = null
        fun playAudio(context: Context?, url: String?) {
            AsyncTask.execute {
                if (mediaPlayer == null) {
                    mediaPlayer = MediaPlayer.create(context, Uri.parse(url))
                }
                mediaPlayer!!.setOnCompletionListener { killMediaPlayer() }
                mediaPlayer!!.start()
            }

        }

         fun killMediaPlayer() {
            if (mediaPlayer != null) {
                try {
                    mediaPlayer!!.reset()
                    mediaPlayer!!.release()
                    mediaPlayer = null
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

}