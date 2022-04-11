package com.myproject.app.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.myproject.app.R
import com.myproject.app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottomView = binding.bottomNavigationView
        val navController = findNavController(R.id.nav_host_fragment_content_main)

        NavigationUI.setupWithNavController(bottomView, navController)

    }
}