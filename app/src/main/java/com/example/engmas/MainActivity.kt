package com.example.engmas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.engmas.ui.EngMasApp
import com.example.engmas.ui.theme.EngMasTheme
import com.google.firebase.FirebaseApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       FirebaseApp.initializeApp(this)

        enableEdgeToEdge()
        setContent {
            EngMasTheme {
                EngMasApp()
            }
        }
    }
}