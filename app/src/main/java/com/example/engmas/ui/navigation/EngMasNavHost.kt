package com.example.engmas.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.engmas.ui.screens.account.AccountDestination
import com.example.engmas.ui.screens.auth.login.LoginScreen
import com.example.engmas.ui.screens.challenge.ChallengeDestination
import com.example.engmas.ui.screens.challenge.ChallengeScreen
import com.example.engmas.ui.screens.challenge.online.ChallengeOnlineDestination
import com.example.engmas.ui.screens.challenge.online.Challenge_OnlineUiState
import com.example.engmas.ui.screens.challenge.online.Challenge_PlayOnlineScreen
import com.example.engmas.ui.screens.challenge.online.ResultState
import com.example.engmas.ui.screens.exam.ExamDestination
import com.example.engmas.ui.screens.exam.ExamScreen
import com.example.engmas.ui.screens.home.HomeDestination
import com.example.engmas.ui.screens.home.HomeScreen
import com.example.engmas.ui.screens.practice.PracticeDestination
import com.example.engmas.ui.screens.practice.PracticeScreen
import androidx.navigation.navigation
import com.example.engmas.ui.screens.auth.signup.SignUpScreen

@Composable
fun EngMasNavHost(
    navController: NavHostController,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,

        // startDestination = HomeDestination.route,

        // QQ:
        startDestination = AccountDestination.route,

        modifier = modifier
    ) {
        composable(route = HomeDestination.route) {
            HomeScreen()
        }

        composable(route = PracticeDestination.route) {
            PracticeScreen()
        }

        composable(route = ExamDestination.route) {
            ExamScreen()
//            Exam_SelectScreen()
//            Challenge_MatchingScreen()
        }

        // main-route: challenge
        composable(route = ChallengeDestination.route) {
            ChallengeScreen(
                onOnlineButtonClicked = { navController.navigate(ChallengeOnlineDestination.route) }
            )
        }
        // sub-route: challenge/online
        composable(route = ChallengeOnlineDestination.route) {
//            Challenge_OnlineScreen()
            Challenge_PlayOnlineScreen(
                uiState = Challenge_OnlineUiState(
                    resultState = ResultState.Win
                ),
                contentPadding = contentPadding,
                onSkipButton = {},
                onSubmitButton = {},
                onExitButton = {},
                onPlayAgainButton = {}
            )
        }

        // QQ:
        // Account (Login - SignUp)
        navigation(
            startDestination = "login",
            route = AccountDestination.route
        ) {
            composable("login") {
                LoginScreen(
                    navController = navController // Truyền navController vào
                )
            }

            composable("signup") {
                SignUpScreen(
                    navController = navController // Truyền navController vào
                )
            }
        }
    }
}