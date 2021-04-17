package com.github.ainul.musica.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.ainul.musica.R

class PlayerActivity: AppCompatActivity() {

    // private lateinit var binding: ActivityPlayerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // binding = DataBindingUtil.setContentView(this, R.layout.activity_player)

        setContentView(R.layout.activity_player)
    }
}