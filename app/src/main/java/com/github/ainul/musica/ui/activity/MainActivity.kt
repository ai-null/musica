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
    private val model: MainViewModel by lazy {
        ViewModelProvider(this,
            MainViewModel.Factory(this.application)).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
    }

    override fun onStart() {
        super.onStart()

        model.currentMusic.observe(this, {
            it?.let {
                Toast.makeText(this, "$it", Toast.LENGTH_SHORT).show()
                // TODO: start motion animation programmatically
            }
        })
    }
}