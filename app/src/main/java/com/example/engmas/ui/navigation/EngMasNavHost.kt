package com.example.engmas.ui.navigation

import android.widget.Toast
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.engmas.ui.screens.account.AccountDestination
import com.example.engmas.ui.screens.account.AccountScreen
import com.example.engmas.ui.screens.auth.login.AuthLoginDestination
import com.example.engmas.ui.screens.auth.login.LoginScreen
import com.example.engmas.ui.screens.auth.login.LoginState
import com.example.engmas.ui.screens.auth.login.LoginViewModel
import com.example.engmas.ui.screens.auth.signup.AuthSignUpDestination
import com.example.engmas.ui.screens.auth.signup.SignUpScreen
import com.example.engmas.ui.screens.challenge.ChallengeDestination
import com.example.engmas.ui.screens.challenge.ChallengeScreen
import com.example.engmas.ui.screens.challenge.offline.ChallengeOfflineDestination
import com.example.engmas.ui.screens.challenge.offline.Challenge_OfflineScreen
import com.example.engmas.ui.screens.challenge.online.ChallengeOnlineDestination
import com.example.engmas.ui.screens.challenge.online.Challenge_OnlineScreen
import com.example.engmas.ui.screens.challenge.online.Challenge_OnlineUiState
import com.example.engmas.ui.screens.challenge.online.Challenge_PlayOnlineScreen
import com.example.engmas.ui.screens.challenge.online.ResultState
import com.example.engmas.ui.screens.challenge.scoreboard.ChallengeScoreBoardDestination
import com.example.engmas.ui.screens.challenge.scoreboard.Challenge_ScoreBoardScreen
import com.example.engmas.ui.screens.exam.ExamDestination
import com.example.engmas.ui.screens.exam.ExamScreen
import com.example.engmas.ui.screens.exam.Exam_SelectScreen
import com.example.engmas.ui.screens.home.HomeDestination
import com.example.engmas.ui.screens.home.HomeScreen
import com.example.engmas.ui.screens.practice.PracticeDestination
import com.example.engmas.ui.screens.practice.PracticeScreen

@Composable
fun EngMasNavHost(
    navController: NavHostController,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val loginViewModel: LoginViewModel = viewModel()
    val loginState by loginViewModel.loginState

    NavHost(
        navController = navController,
        startDestination = if (loginState == LoginState.Success)
            HomeDestination.route
        else AuthLoginDestination.route,
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
                onOnlineButtonClicked = { navController.navigate(ChallengeOnlineDestination.route) },
                onOfflineButtonClicked = { navController.navigate(ChallengeOfflineDestination.route) },
                onScoreBoardButtonClicked = { navController.navigate(ChallengeScoreBoardDestination.route) }
            )
        }
        // sub-route: challenge/online
        composable(route = ChallengeOnlineDestination.route) {
            Challenge_OnlineScreen()
//            Challenge_PlayOnlineScreen(
//                uiState = Challenge_OnlineUiState(
//                    resultState = ResultState.Win
//                ),
//                contentPadding = contentPadding,
//                onSkipButton = {},
//                onSubmitButton = {},
//                onExitButton = {},
//                onPlayAgainButton = {}
//            )
        }
        // sub-route: challenge/offline
        composable(route = ChallengeOfflineDestination.route) {
            Challenge_OfflineScreen()
        }
        // sub-route: challenge/scoreboard
        composable(route = ChallengeScoreBoardDestination.route) {
            Challenge_ScoreBoardScreen()
        }

        composable(route = AccountDestination.route) {
            AccountScreen()
        }

        composable(route = AuthLoginDestination.route) {
            LoginScreen(
                onSignUp = { navController.navigate(AuthSignUpDestination.route) },
                loginViewModel = loginViewModel
            )
        }
        composable(route = AuthSignUpDestination.route) {
            SignUpScreen(
                onSignUpSuccessfully = {
                    navController.navigate(AuthLoginDestination.route) {
                        popUpTo(AuthSignUpDestination.route) { inclusive = true }
                    }
                },
                onLogin = {
                    navController.navigate(AuthLoginDestination.route)
                }
            )
        }
    }

    LaunchedEffect(loginState) {
        when (loginState) {
            LoginState.Idle -> {
                if (navController.currentDestination?.route != AuthLoginDestination.route) {
                    navController.navigate(AuthLoginDestination.route) {
                        popUpTo(0)
                    }
                }
            }
            LoginState.Success -> {
                if (navController.currentDestination?.route == AuthLoginDestination.route) {
                    navController.navigate(HomeDestination.route) {
                        popUpTo(AuthLoginDestination.route) { inclusive = true }
                    }
                }
            }
            LoginState.Error -> {
                Toast.makeText(context, loginViewModel.errorMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }
}