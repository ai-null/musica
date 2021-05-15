package com.github.ainul.musica.ui.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.github.ainul.musica.model.AudioModel
import com.github.ainul.musica.util.AudioUtil
import kotlinx.coroutines.*

class MainViewModel(private val app: Application) : AndroidViewModel(app) {
    // uiScope to manage UI-thread
    private val uiScope = CoroutineScope(Dispatchers.Main + Job())
    // contain list of audio files from devices
    private val _listAudio = MutableLiveData<List<AudioModel>>()
    val listAudio: LiveData<List<AudioModel>> get() = _listAudio

    /** Init, begin retrieving audio files from device*/
    init {
        uiScope.launch {
            val audioFiles = AudioUtil(app.applicationContext).getAudioFromDevice()
            _listAudio.value = audioFiles
        }
    }

    /**
     * these piece of codes used to communicate with parent activity.
     *
     * user clicked on one of the music list, it call [onItemClicked]
     * then it set the [_currentMusic] to what user just clicked
     * state watcher on parentActivity will be notified
     */
    private val _currentMusic = MutableLiveData<AudioModel?>()
    val currentMusic: LiveData<AudioModel?> get() = _currentMusic
    fun onItemClicked(data: AudioModel) {
        _currentMusic.value = data
    }

//    class Factory(private val app: Application) : ViewModelProvider.Factory {
//        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
//                @Suppress("UNCHECKED_CAST")
//                return MainViewModel(app) as T
//            }
//
//            throw ClassCastException("Unable to construct viewmodel")
//        }
//    }
}