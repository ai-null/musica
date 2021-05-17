package com.github.ainul.musica.util

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import java.io.File

class PlayerUtil(private val context: Context, private val song: String) {
    private var _mediaPlayer: MediaPlayer? = null
    private val mediaPlayer: MediaPlayer get() = _mediaPlayer!!

    init {
        create()
    }

    private fun create(newSong: String? = null) {
        val file = File(newSong ?: song)
        val pathToUri = Uri.fromFile(file)
        MediaPlayer.create(context, pathToUri)
    }

    fun play() {
        if (mediaPlayer.isPlaying) pause()
        else mediaPlayer.start()
    }

    private fun pause() {
        mediaPlayer.pause()
    }

    fun destroy() {
        mediaPlayer.stop()
        mediaPlayer.release()
        _mediaPlayer = null
    }
}