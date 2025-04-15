package com.example.engmas.ui.screens.challenge.offline

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.engmas.R
import com.example.engmas.ui.navigation.NavigationDestination
import com.example.engmas.ui.screens.challenge.online.ResultState
import com.example.engmas.ui.utils.UnscrambleGameContent

@Composable
fun Challenge_PlayOfflineScreen(
    modifier: Modifier = Modifier
) {
    var resultState by remember { mutableStateOf(ResultState.None) }
    Card(
        elevation = CardDefaults.cardElevation(4.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD)),
        modifier = modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.padding_medium)),
    ) {
        UnscrambleGameContent(
            unscrambleWord = "Comfortable",
            resultState = resultState,
            onSkipButton = {},
            onSubmitButton = {},
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun Challenge_PlayOfflineScreenPreview() {
    Challenge_PlayOfflineScreen()
}