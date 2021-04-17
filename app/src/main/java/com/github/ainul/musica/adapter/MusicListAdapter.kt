package com.github.ainul.musica.adapter

import android.content.Context
import android.media.MediaMetadataRetriever
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.github.ainul.musica.databinding.ListItemMusicBinding
import com.github.ainul.musica.model.AudioModel
import com.github.ainul.musica.util.albumArt

class MusicDiffUtil : DiffUtil.ItemCallback<AudioModel>() {
    override fun areItemsTheSame(oldItem: AudioModel, newItem: AudioModel): Boolean {
        return oldItem.name == newItem.name || oldItem.album == newItem.album || oldItem.path == newItem.path
    }

    override fun areContentsTheSame(oldItem: AudioModel, newItem: AudioModel): Boolean {
        return oldItem == newItem
    }
}

class MusicListAdapter(private val context: Context) :
    ListAdapter<AudioModel, MusicListAdapter.ViewHolder>(MusicDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = ListItemMusicBinding.inflate(inflater, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)

        holder.binding.data = item

        val path = item.path
        val retriever = MediaMetadataRetriever()

        // get the meta data from music's path
        try { retriever.setDataSource(path) }
        catch (e: IllegalArgumentException) {
            Log.i("ART", "path=$path")
            Log.i("ART_ERROR", "error=${e.message}&cause=${e.cause}")
        }

        // get the embeddedPicture
        val art: ByteArray? = retriever.embeddedPicture
        retriever.release()

        // set image if there's one otherwise ignore
        art?.let {
            Glide.with(context).asBitmap().load(it).into(holder.binding.cover)
        }
    }

    class ViewHolder(val binding: ListItemMusicBinding) : RecyclerView.ViewHolder(binding.root)
}