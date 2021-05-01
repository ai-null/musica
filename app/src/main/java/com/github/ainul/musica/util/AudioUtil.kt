package com.github.ainul.musica.util

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import com.github.ainul.musica.model.AudioModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AudioUtil(private val context: Context) {

    @Suppress("DEPRECATION")
    @SuppressLint("InlinedApi")
    suspend fun getAudioFromDevice(): List<AudioModel> {
        val tempAudioList = ArrayList<AudioModel>()

        val uri: Uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val projection: Array<String> = arrayOf(
            MediaStore.Audio.AudioColumns.DATA, MediaStore.Audio.AudioColumns.TITLE,
            MediaStore.Audio.AudioColumns.ALBUM, MediaStore.Audio.ArtistColumns.ARTIST
        )
        val cursor: Cursor? = context.contentResolver.query(
            uri,
            projection,
            null,
            null,
            null
        )

        // if cursor isn't null, run on the IO thread
        cursor?.let {
            withContext(Dispatchers.IO) {
                while (it.moveToNext()) {
                    val audioModel = AudioModel(
                        path = it.getString(0),
                        name = it.getString(1),
                        album = it.getString(2),
                        artist = it.getString(3),
                    )

                    tempAudioList.add(audioModel)
                }
            }
        }

        // close use of contentResolver
        cursor?.close()

        return tempAudioList
    }
}