package com.github.ainul.musica.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.github.ainul.musica.R
import com.github.ainul.musica.databinding.ActivityMainBinding
import com.github.ainul.musica.ui.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    // define dataBinding and viewmodel
    private lateinit var binding: ActivityMainBinding
    private val viewmodel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.viewmodel = viewmodel

        updateLiveData()
        componentListener()
    }

    private fun updateLiveData() {
        viewmodel.currentMusic.observe(this, {
            it?.let {
                binding.mainContainer.transitionToEnd()
            }
        })
    }

    private fun componentListener() {
        // TODO: Add some play listener
    }
}