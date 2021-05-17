package com.github.ainul.musica.util

import android.media.MediaMetadataRetriever
import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.github.ainul.musica.R

/**
 * Use to set album art of the song to the [ImageView]
 *
 * @param path String
 *  path of the audio files
 */
@BindingAdapter("albumArt")
fun ImageView.albumArt(path: String?) {
    val retriever = MediaMetadataRetriever()
    val glide = Glide.with(this)

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

    // set embedded picture
    if (art !== null) glide.asBitmap().load(art).into(this)
    else glide.load(R.drawable.background_cover).into(this)
}