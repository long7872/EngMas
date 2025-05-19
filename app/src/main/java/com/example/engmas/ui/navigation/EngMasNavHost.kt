package com.example.engmas.ui.navigation

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.engmas.ui.GeneralViewModel
import com.example.engmas.ui.screens.account.AccountAchievementDestination
import com.example.engmas.ui.screens.account.AccountDestination
import com.example.engmas.ui.screens.account.AccountFriendDestination
import com.example.engmas.ui.screens.account.AccountInformationDestination
import com.example.engmas.ui.screens.account.AccountScreen
import com.example.engmas.ui.screens.account.AchievementScreen
import com.example.engmas.ui.screens.account.FriendScreen
import com.example.engmas.ui.screens.account.InformationScreen
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
import com.example.engmas.ui.screens.challenge.scoreboard.ChallengeScoreBoardDestination
import com.example.engmas.ui.screens.challenge.scoreboard.Challenge_ScoreBoardScreen
import com.example.engmas.ui.screens.chat.chatbot_gemini.ChatDestination
import com.example.engmas.ui.screens.chat.chatbot_gemini.ChatPage
import com.example.engmas.ui.screens.exam.ExamDestination
import com.example.engmas.ui.screens.exam.ExamProcessing
import com.example.engmas.ui.screens.exam.ExamScreen
import com.example.engmas.ui.screens.exam.ExamStartDestination
import com.example.engmas.ui.screens.home.HomeDestination
import com.example.engmas.ui.screens.home.HomeScreen
import com.example.engmas.ui.screens.home.HomeSearchDestination
import com.example.engmas.ui.screens.home.SearchScreen
import com.example.engmas.ui.screens.practice.PracticeDestination
import com.example.engmas.ui.screens.practice.PracticeScreen
import com.example.engmas.ui.screens.practice.courses.CourseProcessing
import com.example.engmas.ui.screens.practice.courses.CourseScreen
import com.example.engmas.ui.screens.practice.courses.FlashcardScreen
import com.example.engmas.ui.screens.practice.courses.PracticeCourseDestination
import com.example.engmas.ui.screens.practice.courses.PracticeFlashcardDestination
import com.example.engmas.ui.screens.practice.courses.PracticeReviewDestination
import com.example.engmas.ui.screens.practice.courses.ReviewScreen
import com.example.engmas.ui.screens.practice.flashcard.FlashcardInTopic
import com.example.engmas.ui.screens.practice.flashcard.PracticeFlashCardInTopicDestination
import com.example.engmas.ui.screens.practice.grammar.GrammarContent
import com.example.engmas.ui.screens.practice.grammar.GrammarScreen
import com.example.engmas.ui.screens.practice.grammar.PracticeGrammarDestination
import com.example.engmas.ui.screens.practice.grammar.PracticeGrammarsDestination
import com.example.engmas.ui.screens.practice.grammar.data.GrammarItems
import com.example.engmas.ui.screens.practice.vocabulary.PracticeVocabularyDestination
import com.example.engmas.ui.screens.practice.vocabulary.PracticeVocabularyLearningDestination
import com.example.engmas.ui.screens.practice.vocabulary.VocabularyProcessing
import com.example.engmas.ui.screens.practice.vocabulary.VocabularyScreen
import com.example.engmas.ui.screens.practice.voices.PracticeVoiceDestination
import com.example.engmas.ui.screens.practice.voices.VoiceScreen

@Composable
fun EngMasNavHost(
    generalViewModel: GeneralViewModel,
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
            HomeScreen(
                timerViewModel = generalViewModel,
                onCourseClicked = {
                    navController.navigate("${PracticeCourseDestination.route}/$it")
                    Log.d("Eng Mas Nav Host", "Home on course click: ${PracticeCourseDestination.route}/$it")
                },
                onSearchItemClicked = {
                    navController.navigate("${HomeSearchDestination.route}/$it")
                }
            )
        }

        composable(route = ChatDestination.route) {
            ChatPage()
        }

        composable(
            route = HomeSearchDestination.routeWithArgs,
            arguments = listOf(
                navArgument(HomeSearchDestination.ITEM_ARGS)
                { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val vocabId = backStackEntry.arguments?.getInt(
                HomeSearchDestination.ITEM_ARGS
            )
            if (vocabId != null) {
                SearchScreen(
                    vocabId = vocabId
                )
            } else {
                navController.navigate(HomeDestination.route)
            }
        }

        composable(route = PracticeDestination.route) {
            PracticeScreen(
                onVocabularyClicked = { navController.navigate(PracticeVocabularyDestination.route) },
                onGrammarClicked = { navController.navigate(PracticeGrammarsDestination.route) },
                onFlashCardClicked = { navController.navigate(PracticeFlashcardDestination.route) },
                onReviewClicked = { navController.navigate(PracticeReviewDestination.route) },
                onCourseClicked = {
                    navController.navigate("${PracticeCourseDestination.route}/$it")
                    Log.d("Eng Mas Nav Host", "Practice on course click: ${PracticeCourseDestination.route}/$it")
                },
                onVoiceClicked = { navController.navigate(PracticeVoiceDestination.route) }
            )
        }

        composable(
            route = PracticeCourseDestination.routeWithArgs,
            arguments = listOf(
                navArgument(PracticeCourseDestination.ITEM_ARGS)
                { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val courseId = backStackEntry.arguments?.getInt(
                PracticeCourseDestination.ITEM_ARGS
            )
            if (courseId != null) {
                CourseProcessing(
                    courseId = courseId,
                    onBackClicked = { navController.navigateUp() }
                )
            } else {
                navController.navigate(HomeDestination.route)
            }
        }

        composable(route = PracticeVocabularyDestination.route) {
            VocabularyScreen(
                onClick = { navController.navigate(
                    "${PracticeVocabularyLearningDestination.route}/$it"
                ) },
                onBackClicked = { navController.navigateUp() }
            )
        }

        composable(
            route = PracticeVocabularyLearningDestination.routeWithArgs,
            arguments = listOf(
                navArgument(PracticeVocabularyLearningDestination.ITEM_ARGS)
                    { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val topicId = backStackEntry.arguments?.getInt(
                PracticeVocabularyLearningDestination.ITEM_ARGS
            )
            if (topicId != null) {
                VocabularyProcessing(
                    topicId = topicId,
                    onBackClicked = { navController.navigateUp() }
                )
            } else {
                navController.navigate(HomeDestination.route)
            }
        }

        composable(route = PracticeGrammarsDestination.route) {
            GrammarScreen(
                onClick = { navController.navigate("${PracticeGrammarDestination.route}/${it.grammarLink}") },
                navigateUp = { navController.navigateUp() }
            )
        }

        composable(
            route = PracticeGrammarDestination.routeWithArgs,
            arguments = listOf(navArgument(PracticeGrammarDestination.ITEM_ARGS) { type = NavType.StringType })
        ) { backStackEntry ->
            val grammarLink = backStackEntry.arguments?.getString(PracticeGrammarDestination.ITEM_ARGS)

            val grammarItem = GrammarItems.firstOrNull { it.grammarLink == grammarLink }

            if (grammarItem != null) {
                GrammarContent(
                    item = grammarItem,
                    navigateUp = { navController.navigateUp() }
                )
            } else {
                navController.navigate(HomeDestination.route)
            }
        }

        composable(route = PracticeFlashcardDestination.route) {
            FlashcardScreen(
                onClick = { navController.navigate(
                    "${PracticeFlashCardInTopicDestination.route}/$it"
                ) },
                onBackClicked = { navController.navigateUp() }
            )
        }

        composable(
            route = PracticeFlashCardInTopicDestination.routeWithArgs,
            arguments = listOf(
                navArgument(PracticeVocabularyLearningDestination.ITEM_ARGS)
                { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val topicId = backStackEntry.arguments?.getInt(
                PracticeVocabularyLearningDestination.ITEM_ARGS
            )
            if (topicId != null) {
                FlashcardInTopic(
                    topicId = topicId,
                    onBackClicked = { navController.navigateUp() }
                )
            } else {
                navController.navigate(HomeDestination.route)
            }
        }

        composable(route = PracticeReviewDestination.route) {
            ReviewScreen(
                onVocabularyClick = { navController.navigate(
                    "${PracticeVocabularyLearningDestination.route}/$it"
                ) },
                onFlashcardClick = { navController.navigate(
                    "${PracticeFlashCardInTopicDestination.route}/$it"
                ) },
                onBackClicked = { navController.navigateUp() }
            )
        }

        composable(route = PracticeVoiceDestination.route) {
            VoiceScreen(
                onBackClicked = { navController.navigateUp() }
            )
        }

        composable(route = ExamDestination.route) {
            ExamScreen(
                onClicked = { navController.navigate("${ExamStartDestination.route}/$it") }
            )
        }

        composable(
            route = ExamStartDestination.routeWithArgs,
            arguments = listOf(
                navArgument(ExamStartDestination.ITEM_ARGS)
                { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val examId = backStackEntry.arguments?.getInt(
                ExamStartDestination.ITEM_ARGS
            )
            if (examId != null) {
                ExamProcessing(
                    examId = examId,
                    navigateUp = { navController.navigate(ExamDestination.route) }
                )
            } else {
                navController.navigate(HomeDestination.route)
            }
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
            Challenge_OnlineScreen(
                onExitButton = { navController.navigate(ChallengeDestination.route) }
            )
        }
        // sub-route: challenge/offline
        composable(route = ChallengeOfflineDestination.route) {
            Challenge_OfflineScreen(
                onExit = { navController.navigate(ChallengeDestination.route) }
            )
        }
        // sub-route: challenge/scoreboard
        composable(route = ChallengeScoreBoardDestination.route) {
            Challenge_ScoreBoardScreen()
        }

        composable(route = AccountDestination.route) {
            AccountScreen(
                onInfoClicked = { navController.navigate(AccountInformationDestination.route) },
                onAchievementClicked = { navController.navigate(AccountAchievementDestination.route) },
                onFriendClicked = { navController.navigate(AccountFriendDestination.route) },
                onSignOutClicked = { loginViewModel.logout() },
                onDeleteAccount = { loginViewModel.deleteAccount() }
            )
        }

        composable(route = AccountInformationDestination.route) {
            InformationScreen(
                onBackClicked = { navController.navigateUp() }
            )
        }

        composable(route = AccountAchievementDestination.route) {
            AchievementScreen(
                onBackClicked = { navController.navigateUp() }
            )
        }

        composable(route = AccountFriendDestination.route) {
            FriendScreen(
                onBackClicked = { navController.navigateUp() }
            )
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