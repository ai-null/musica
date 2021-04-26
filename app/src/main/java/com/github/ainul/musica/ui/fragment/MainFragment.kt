package com.github.ainul.musica.ui.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.github.ainul.musica.adapter.ClickListener
import com.github.ainul.musica.adapter.MusicListAdapter
import com.github.ainul.musica.adapter.RecentPlaylistAdapter
import com.github.ainul.musica.databinding.FragmentMainBinding
import com.github.ainul.musica.ui.activity.PlayerActivity
import com.github.ainul.musica.ui.viewmodel.MainViewModel

class MainFragment : Fragment(), ClickListener {

    private lateinit var binding: FragmentMainBinding
    private lateinit var model: MainViewModel

    private lateinit var musicAdapter: MusicListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // setup ViewModel and dataBinding
        val context = requireNotNull(this.context)
        binding = FragmentMainBinding.inflate(layoutInflater, container, false)
        model = ViewModelProvider(this, MainViewModel.Factory(context)).get(MainViewModel::class.java)

        setupListAdapter(context)
        updateLiveData()
        return binding.root
    }

    private fun setupListAdapter(context: Context) {
        val recentAdapter = RecentPlaylistAdapter(context, this)
        val sampleData: List<Int> = listOf(1, 2, 3, 4, 5)

        recentAdapter.data = sampleData
        binding.recentPlaylist.adapter = recentAdapter

        musicAdapter = MusicListAdapter(context)
        binding.musicList.adapter = musicAdapter
    }

    private fun updateLiveData() {
        model.listAudio.observe(viewLifecycleOwner, {
            musicAdapter.submitList(it.slice(0..4))
        })
    }

    override fun onClick() {
        val playerIntent = Intent(context, PlayerActivity::class.java)

        startActivity(playerIntent)
    }
}