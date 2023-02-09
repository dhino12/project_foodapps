package com.example.foodapplication.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.bumptech.glide.Glide
import com.example.foodapplication.ui.theme.MyRecipesAppTheme

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyRecipesAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    RecipeApp()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Glide.get(this).clearMemory()
        finish()
    }
}