package com.github.ainul.musica.ui.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.ainul.musica.model.AudioModel
import kotlinx.coroutines.*

class MainViewModel(private val context: Context) : ViewModel() {

    private val uiScope = CoroutineScope(Dispatchers.Main + Job())

    private val _listAudio = MutableLiveData<List<AudioModel>>()
    val listAudio: LiveData<List<AudioModel>> get() = _listAudio

    @Suppress("DEPRECATION")
    @SuppressLint("InlinedApi")
    private suspend fun getAudioFromDevice(): List<AudioModel> {
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

    init {
        uiScope.launch {
            val audioFiles = getAudioFromDevice()
            _listAudio.value = audioFiles
        }
    }

    class Factory(private val context: Context) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(context) as T
            }

            throw ClassCastException("Unable to construct viewmodel")
        }
    }
}