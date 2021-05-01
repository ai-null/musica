package com.github.ainul.musica.adapter

import com.github.ainul.musica.model.AudioModel

interface ClickListener {
    fun onClick(data: AudioModel)
}