package com.github.ainul.musica.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.ainul.musica.databinding.GridItemMusicBinding

class RecentPlaylistAdapter(
    private val context: Context,
    private val ClickListener: ClickListener
) :
    RecyclerView.Adapter<RecentPlaylistAdapter.ViewHolder>() {
    var data: List<Int> = listOf()
        set(value) {
            notifyDataSetChanged()
            field = value
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(context, parent, ClickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }

    override fun getItemCount() = data.size

    class ViewHolder(
        private val binding: GridItemMusicBinding,
        private val clickListener: ClickListener
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Int) {
            binding.coverContainer.setOnClickListener {
                clickListener.onClick()
            }
        }

        companion object {
            fun from(
                context: Context,
                parent: ViewGroup,
                ClickListener: ClickListener
            ): ViewHolder {
                val inflater = LayoutInflater.from(context)
                val view = GridItemMusicBinding.inflate(inflater, parent, false)

                return ViewHolder(view, ClickListener)
            }
        }
    }
}