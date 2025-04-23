package com.example.engmas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.engmas.data.UserDataUploader
import com.example.engmas.ui.EngMasApp
import com.example.engmas.ui.screens.challenge.ChallengeScreen
import com.example.engmas.ui.screens.challenge.scoreboard.Challenge_ScoreBoardScreen
import com.example.engmas.ui.screens.home.HomeScreen
import com.example.engmas.ui.theme.EngMasTheme
import com.google.firebase.FirebaseApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Khởi tạo Firebase nếu chưa được khởi tạo
        FirebaseApp.initializeApp(this)

        // Gọi upload dữ liệu
        UserDataUploader().uploadData()

        enableEdgeToEdge()
        setContent {
            EngMasTheme {
                Challenge_ScoreBoardScreen()
            }
        }
    }
}