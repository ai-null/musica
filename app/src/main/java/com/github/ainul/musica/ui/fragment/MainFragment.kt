package com.github.ainul.musica.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.github.ainul.musica.adapter.ClickListener
import com.github.ainul.musica.adapter.MusicListAdapter
import com.github.ainul.musica.adapter.RecentPlaylistAdapter
import com.github.ainul.musica.databinding.FragmentMainBinding
import com.github.ainul.musica.model.AudioModel
import com.github.ainul.musica.ui.viewmodel.MainViewModel

class MainFragment : Fragment(), ClickListener {

    private lateinit var binding: FragmentMainBinding
    private val model: MainViewModel by activityViewModels {
        MainViewModel.Factory(requireActivity().application)
    }

    private lateinit var musicAdapter: MusicListAdapter
    private lateinit var recentAdapter: RecentPlaylistAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // setup ViewModel and dataBinding
        binding = FragmentMainBinding.inflate(layoutInflater, container, false)

        updateLiveData()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val context = requireNotNull(this.context)

        // recent playlist
        recentAdapter = RecentPlaylistAdapter(context, this)
        binding.recentPlaylist.adapter = recentAdapter

        // list music on device
        musicAdapter = MusicListAdapter(context)
        binding.musicList.adapter = musicAdapter
    }

    private fun updateLiveData() {
        // Audio retriever
        model.listAudio.observe(viewLifecycleOwner, {
            musicAdapter.submitList(it.slice(0..4))
            recentAdapter.data = it.slice(6..12)
        })
    }

    override fun onClick(data: AudioModel) {
        model.onItemClicked(data)
    }
}