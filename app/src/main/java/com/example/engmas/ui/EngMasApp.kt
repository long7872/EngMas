package com.example.engmas.ui

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
import androidx.compose.runtime.Composable
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.engmas.R
import com.example.engmas.ui.navigation.EngMasNavHost
import com.example.engmas.ui.navigation.NavigationDestination
import com.example.engmas.ui.screens.account.AccountDestination
import com.example.engmas.ui.screens.challenge.ChallengeDestination
import com.example.engmas.ui.screens.challenge.online.ChallengeOnlineDestination
import com.example.engmas.ui.screens.challenge.online.Challenge_OnlineUiState
import com.example.engmas.ui.screens.challenge.online.Challenge_PlayOnlineScreen
import com.example.engmas.ui.screens.challenge.online.ResultState
import com.example.engmas.ui.screens.exam.ExamDestination
import com.example.engmas.ui.screens.home.HomeDestination
import com.example.engmas.ui.screens.home.HomeScreen
import com.example.engmas.ui.screens.practice.PracticeDestination
import com.example.engmas.ui.theme.InterFont
import com.example.engmas.ui.theme.KufamFont

@Composable
fun EngMasApp(
    navController: NavHostController = rememberNavController()
) {
    val allDestinations = listOf<NavigationDestination>(
        HomeDestination,
        PracticeDestination,
        ExamDestination,
        ChallengeDestination,
        ChallengeOnlineDestination,
        AccountDestination,
    )
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentEngMasScreen = allDestinations.find {
        it.route == backStackEntry?.destination?.route
    } ?: HomeDestination

    var selectedIndex by remember { mutableIntStateOf(0) }
    val isChallengeSubRoute by remember {
        mutableStateOf(currentEngMasScreen.route.startsWith(ChallengeDestination.route))
    }

    when (currentEngMasScreen) {
        is HomeDestination -> selectedIndex = 1
        is PracticeDestination -> selectedIndex = 2
        is ExamDestination -> selectedIndex = 3
        is ChallengeDestination -> selectedIndex = 4
        is AccountDestination -> selectedIndex = 5
        else -> selectedIndex = 0
    }

    Scaffold(
        topBar = { EngMasTopAppBar(
            canNavigateBack = selectedIndex == 0,
            navigateUp = {
                if (isChallengeSubRoute) {
                    navController.navigate(ChallengeDestination.route)
                } else {
                    navController.navigateUp()
                }
            }
        ) },
        bottomBar = { EngMasBottomNavigationBar(
            currentDestination = currentEngMasScreen,
            navController = navController
        ) },
        containerColor = Color(0xFFF5F5F5)
    ) { contentPadding ->
        EngMasNavHost(
            navController = navController,
            contentPadding = contentPadding,
            modifier = Modifier.padding(contentPadding)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EngMasTopAppBar(
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
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
                onClick = {},
                modifier = Modifier.padding(end = dimensionResource(R.dimen.padding_medium))
                    .size(dimensionResource(R.dimen.icon_size)
                            + dimensionResource(R.dimen.padding_small))
            ) {
                Icon(
                    painter = painterResource(R.drawable.chat_icon),
                    tint = Color(0xFF2B4EA2),
                    contentDescription = stringResource(R.string.chat),
                    modifier = Modifier.size(dimensionResource(R.dimen.icon_size))
                )
            }
        },
        modifier = modifier
    )
}

@Composable
fun EngMasBottomNavigationBar(
    currentDestination: NavigationDestination,
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
    var selectedIndex by remember { mutableIntStateOf(0) }

    selectedIndex = when (currentDestination) {
        is HomeDestination -> 1
        is PracticeDestination -> 2
        is ExamDestination -> 3
        is ChallengeDestination -> 4
        is AccountDestination -> 5
        else -> 0
    }

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