package com.example.engmas.ui.screens.challenge.offline

import android.app.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.engmas.R
import com.example.engmas.ui.navigation.NavigationDestination

object ChallengeOfflineDestination: NavigationDestination {
    override val route = "challenge/offline"
    override val titleRes = R.string.tab_challenge_offline
}

@Composable
fun Challenge_OfflineScreen(
    modifier: Modifier = Modifier
) {
    Challenge_PlayOfflineScreen()
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun Challenge_OfflineScreenPreview() {
//    Challenge_OfflineScreen()
}