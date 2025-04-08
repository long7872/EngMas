package com.example.engmas.ui.screens.home

import androidx.compose.runtime.Composable
import com.example.engmas.R
import com.example.engmas.ui.navigation.NavigationDestination

object HomeDestination: NavigationDestination {
    override val route = "home"
    override val titleRes = R.string.tab_home
}

@Composable
fun HomeScreen() {

}