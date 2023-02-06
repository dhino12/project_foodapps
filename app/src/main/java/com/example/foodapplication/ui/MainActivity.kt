package com.example.foodapplication.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.example.foodapplication.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)

        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    RecipeApp()
                }
            }
        }

//        val navView: BottomNavigationView = findViewById(R.id.nav_view)
//
//        val navController = findNavController(R.id.nav_host_fragment)
//
//        navView.setupWithNavController(navController)
    }

    override fun onDestroy() {
        super.onDestroy()
        Glide.get(this).clearMemory()
        finish()
    }
}