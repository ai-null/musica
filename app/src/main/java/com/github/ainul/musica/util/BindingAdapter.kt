package com.github.ainul.musica.util

import android.media.MediaMetadataRetriever
import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

/**
 * Use to set album art of the song to the [ImageView]
 *
 * @param path String
 *  path of the audio files
 */
@BindingAdapter("albumArt")
fun ImageView.albumArt(path: String) {
    val retriever = MediaMetadataRetriever()

    // get the meta data from music's path
    try {
        retriever.setDataSource(path)
    } catch (e: IllegalArgumentException) {
        Log.i("ART", "path=$path")
        Log.i("ART_ERROR", "error=${e.message}&cause=${e.cause}")
    }

    // get the embeddedPicture
    val art: ByteArray? = retriever.embeddedPicture
    retriever.release()

    // set image if there's one otherwise ignore
    art?.let {
        Glide.with(this).asBitmap().load(it).into(this)
    }
}