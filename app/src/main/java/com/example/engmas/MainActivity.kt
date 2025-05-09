package com.example.engmas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.engmas.ui.EngMasApp
import com.example.engmas.ui.screens.exam.part1.Part1
import com.example.engmas.ui.screens.exam.part1.Part1ViewModel
import com.example.engmas.ui.screens.exam.part1.Part1_Result
import com.example.engmas.ui.screens.practice.PracticeScreen
import com.example.engmas.ui.screens.practice.grammar.GrammarScreen
import com.example.engmas.ui.theme.EngMasTheme
import com.google.firebase.FirebaseApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       FirebaseApp.initializeApp(this)

        enableEdgeToEdge()
        setContent {
            EngMasTheme {
//               ChatInterface()
                Part1()
            }
        }
    }
}