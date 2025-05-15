package com.example.engmas.ui

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.engmas.R
import com.example.engmas.ui.navigation.EngMasNavHost
import com.example.engmas.ui.navigation.NavigationDestination
import com.example.engmas.ui.screens.account.AccountDestination
import com.example.engmas.ui.screens.auth.login.AuthLoginDestination
import com.example.engmas.ui.screens.auth.signup.AuthSignUpDestination
import com.example.engmas.ui.screens.challenge.ChallengeDestination
import com.example.engmas.ui.screens.challenge.offline.ChallengeOfflineDestination
import com.example.engmas.ui.screens.challenge.online.ChallengeOnlineDestination
import com.example.engmas.ui.screens.challenge.online.Challenge_OnlineUiState
import com.example.engmas.ui.screens.challenge.online.Challenge_PlayOnlineScreen
import com.example.engmas.ui.screens.challenge.online.ResultState
import com.example.engmas.ui.screens.challenge.scoreboard.ChallengeScoreBoardDestination
import com.example.engmas.ui.screens.exam.ExamDestination
import com.example.engmas.ui.screens.exam.ExamStartDestination
import com.example.engmas.ui.screens.home.HomeDestination
import com.example.engmas.ui.screens.home.HomeScreen
import com.example.engmas.ui.screens.practice.PracticeDestination
import com.example.engmas.ui.screens.practice.courses.PracticeCourseDestination
import com.example.engmas.ui.screens.practice.courses.PracticeFlashcardDestination
import com.example.engmas.ui.screens.practice.courses.PracticeReviewDestination
import com.example.engmas.ui.screens.practice.flashcard.PracticeFlashCardInTopicDestination
import com.example.engmas.ui.screens.practice.grammar.PracticeGrammarDestination
import com.example.engmas.ui.screens.practice.grammar.PracticeGrammarsDestination
import com.example.engmas.ui.screens.practice.vocabulary.PracticeVocabularyDestination
import com.example.engmas.ui.theme.InterFont
import com.example.engmas.ui.theme.KufamFont

const val TAG = "MainActivity"

@Composable
fun EngMasApp(
    generalViewModel: GeneralViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {

//    val allDestinations = listOf(
//        HomeDestination,
//        PracticeDestination,
//        PracticeCourseDestination,
//        PracticeVocabularyDestination,
//        PracticeGrammarsDestination,
//        PracticeGrammarDestination,
//        PracticeFlashcardDestination,
//        PracticeFlashCardInTopicDestination,
//        PracticeReviewDestination,
//        ExamDestination,
//        ChallengeDestination,
//        ChallengeOnlineDestination,
//        ChallengeOfflineDestination,
//        ChallengeScoreBoardDestination,
//        AccountDestination,
//        AuthLoginDestination,
//        AuthSignUpDestination
//    )
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    var selectedIndex by remember { mutableIntStateOf(0) }
    // Tính toán các điều kiện sử dụng derivedStateOf
    val isChallengeSubRoute by remember(currentRoute) {
        derivedStateOf { currentRoute?.startsWith(ChallengeDestination.route) == true }
    }
    val isAuthSubRoute by remember(currentRoute) {
        derivedStateOf { currentRoute?.startsWith("auth") == true }
    }
    val isPracticeSubRoute by remember(currentRoute) {
        derivedStateOf { currentRoute?.startsWith(PracticeDestination.route) == true }
    }
    val isExamSubRoute by remember(currentRoute) {
        derivedStateOf { currentRoute?.startsWith(ExamStartDestination.route) == true }
    }
    val isAccountSubRoute by remember(currentRoute) {
        derivedStateOf { currentRoute?.startsWith(AccountDestination.route) == true }
    }
    val isHomeSubRoute by remember(currentRoute) {
        derivedStateOf { currentRoute?.startsWith(HomeDestination.route) == true }
    }

    LaunchedEffect(currentRoute) {
        Log.d("Eng Mas Screen", "current route: $currentRoute")
        Log.d("Eng Mas Screen", "challenge sub route? : $isChallengeSubRoute")
        Log.d("Eng Mas Screen", "auth sub route? : $isAuthSubRoute")
        Log.d("Eng Mas Screen", "practice sub route? : $isPracticeSubRoute")
        Log.d("Eng Mas Screen", "exam sub route? : $isExamSubRoute")
        Log.d("Eng Mas Screen", "account sub route? : $isAccountSubRoute")
        when (currentRoute) {
            HomeDestination.route -> selectedIndex = 1
            PracticeDestination.route -> selectedIndex = 2
            ExamDestination.route -> selectedIndex = 3
            ChallengeDestination.route -> selectedIndex = 4
            AccountDestination.route -> selectedIndex = 5
            else -> selectedIndex = 0
        }
    }



    Scaffold(
        topBar = { EngMasTopAppBar(
            isNotDisplay = isAuthSubRoute || isExamSubRoute,
            canNavigateBack = if (isPracticeSubRoute || isAccountSubRoute) false else selectedIndex == 0,
            navigateUp = {
                if (isChallengeSubRoute) {
                    navController.navigate(ChallengeDestination.route)
                } else {
                    navController.navigateUp()
                }
            },
            onActionButtonClicked = {}
        ) },
        bottomBar = { EngMasBottomNavigationBar(
            selectedIndex = selectedIndex,
            navController = navController
        ) },
        containerColor = Color(0xFFF5F5F5)
    ) { contentPadding ->
        EngMasNavHost(
            generalViewModel = generalViewModel,
            navController = navController,
            contentPadding = contentPadding,
            modifier = Modifier.padding(contentPadding)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EngMasTopAppBar(
    isNotDisplay: Boolean,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    onActionButtonClicked: () -> Unit,
    isSubmit: Boolean = false,
    modifier: Modifier = Modifier
) {
    if (!isNotDisplay) {
        TopAppBar(
//            colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White),
            navigationIcon = {
                if (canNavigateBack) {
                    IconButton(
                        onClick = navigateUp,
                        modifier = Modifier.padding(start = dimensionResource(R.dimen.padding_medium))
                            .size(dimensionResource(R.dimen.icon_size)
                                    + dimensionResource(R.dimen.padding_small))
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.back_icon),
                            tint = Color(0xFF757575),
                            contentDescription = stringResource(R.string.chat),
                            modifier = Modifier.size(dimensionResource(R.dimen.icon_size))
                        )
                    }
                }
            },
            title = {
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(color = Color(0xFF2D5FA7))) { append("Eng") }
                        withStyle(style = SpanStyle(color = Color(0xFF757575))) { append("Mas") }
                    },
                    fontFamily = KufamFont,
                    fontWeight = FontWeight.Black,
                    fontSize = 40.sp,
                    textAlign = if (canNavigateBack) TextAlign.Center else TextAlign.Start,
                    modifier = Modifier.fillMaxWidth()
                        .padding(top = dimensionResource(R.dimen.padding_small))
                )
            },
            actions = {
                IconButton(
                    onClick = onActionButtonClicked,
                    modifier = Modifier.padding(end = dimensionResource(R.dimen.padding_medium))
                        .size(dimensionResource(R.dimen.icon_size)
                                + dimensionResource(R.dimen.padding_small))
                ) {
                    Icon(
                        painter = if (!isSubmit) painterResource(R.drawable.chat_icon)
                            else painterResource(R.drawable.submit_icon),
                        tint = Color(0xFF2B4EA2),
                        contentDescription = if (!isSubmit) stringResource(R.string.chat)
                            else stringResource(R.string.submit),
                        modifier = Modifier.size(dimensionResource(R.dimen.icon_size))
                    )
                }
            },
            modifier = modifier
        )
    }
}

@Composable
fun EngMasBottomNavigationBar(
    selectedIndex: Int,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val navigationItemContentList = listOf(
        NavigationItemContent(
            number = 1,
            icon = ImageVector.vectorResource(R.drawable.home_icon),
            text = stringResource(R.string.tab_home)
        ),
        NavigationItemContent(
            number = 2,
            icon = ImageVector.vectorResource(R.drawable.practice_icon),
            text = stringResource(R.string.tab_practice)
        ),
        NavigationItemContent(
            number = 3,
            icon = ImageVector.vectorResource(R.drawable.exam_icon),
            text = stringResource(R.string.tab_exam)
        ),
        NavigationItemContent(
            number = 4,
            icon = ImageVector.vectorResource(R.drawable.challenge_icon),
            text = stringResource(R.string.tab_challenge)
        ),
        NavigationItemContent(
            number = 5,
            icon = ImageVector.vectorResource(R.drawable.account_icon),
            text = stringResource(R.string.tab_account)
        )
    )

    if (selectedIndex != 0) {
        NavigationBar(
            containerColor = Color(0xFFF5F5F5),
            modifier = modifier.fillMaxWidth()
        ) {
            for (navItem in navigationItemContentList) {
                NavigationBarItem(
                    selected = navItem.number == selectedIndex,
                    enabled = navItem.number != selectedIndex,
                    onClick = {
                        when(navItem.number) {
                            1 -> navController.navigate(HomeDestination.route)
                            2 -> navController.navigate(PracticeDestination.route)
                            3 -> navController.navigate(ExamDestination.route)
                            4 -> navController.navigate(ChallengeDestination.route)
                            5 -> navController.navigate(AccountDestination.route)
                        }
                    },
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = Color(0xFFF5F5F5),
                        selectedIconColor = Color(0xFF2B4EA2),
                        disabledIconColor = Color(0xFF2B4EA2),
                    ),
                    icon = {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = navItem.icon,
                                contentDescription = navItem.text,
                                modifier = Modifier.size(dimensionResource(R.dimen.nav_icon_size))
                            )
                            Spacer(Modifier.height(5.dp))
                            Text(
                                text = navItem.text,
                                fontFamily = InterFont,
                                fontWeight = FontWeight.Normal,
                                fontSize = 8.sp
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth()

                )
            }
        }
    }
}

private data class NavigationItemContent(
    val number: Int,
    val icon: ImageVector,
    val text: String
)

@Preview(showBackground = true)
@Composable
private fun AppPreview() {
    EngMasApp()
}