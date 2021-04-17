package com.github.ainul.musica.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.github.ainul.musica.R
import com.github.ainul.musica.adapter.ClickListener
import com.github.ainul.musica.adapter.MusicListAdapter
import com.github.ainul.musica.adapter.RecentPlaylistAdapter
import com.github.ainul.musica.databinding.ActivityMainBinding
import com.github.ainul.musica.ui.viewmodel.MainViewModel

class MainActivity : AppCompatActivity(), ClickListener {

    // set up binding & viewModel
    private lateinit var binding: ActivityMainBinding
    private val model: MainViewModel by lazy {
        ViewModelProvider(this, MainViewModel.Factory(this)).get(MainViewModel::class.java)
    }

    private lateinit var musicAdapter: MusicListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val recentAdapter = RecentPlaylistAdapter(this, this)
        val sampleData: List<Int> = listOf(1, 2, 3, 4, 5)

        recentAdapter.data = sampleData
        binding.recentPlaylist.adapter = recentAdapter

        musicAdapter = MusicListAdapter(this)
        binding.musicList.adapter = musicAdapter

        updateLiveData()
    }

    private fun updateLiveData() {
        model.listAudio.observe(this, {
            musicAdapter.submitList(it.slice(0..4))
        })
    }

    override fun onClick() {
        val playerIntent = Intent(this, PlayerActivity::class.java)

        startActivity(playerIntent)
    }
}