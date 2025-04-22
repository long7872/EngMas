package com.example.engmas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.engmas.ui.EngMasApp
import com.example.engmas.ui.screens.practice.courses.Content
import com.example.engmas.ui.theme.EngMasTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EngMasTheme {
                EngMasApp()
            }
        }
    }
}