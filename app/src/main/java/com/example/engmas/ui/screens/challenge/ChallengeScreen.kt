package com.example.engmas.ui.screens.challenge

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.engmas.R
import com.example.engmas.ui.navigation.NavigationDestination
import com.example.engmas.ui.theme.KufamFont
import com.example.engmas.ui.utils.CustomButton

object ChallengeDestination: NavigationDestination {
    override val route: String = "challenge"
    override val titleRes: Int = R.string.tab_challenge
}

@Composable
fun ChallengeScreen(
    onOnlineButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        elevation = CardDefaults.cardElevation(4.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD)),
        modifier = modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.padding_medium)),
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.word_scramble),
                contentDescription = "Word Scramble",
                modifier = Modifier.fillMaxWidth()
                    .weight(0.3f)
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFF49C1D9), MaterialTheme.shapes.medium)
                    .clip(MaterialTheme.shapes.medium)
                    .weight(0.7f)
            ) {
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFFFF)),
                    shape = MaterialTheme.shapes.medium,
                    elevation = CardDefaults.cardElevation(4.dp),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = dimensionResource(R.dimen.padding_small)),
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Spacer(Modifier.weight(0.15f))
                        CustomButton(
                            text = "Play Online",
                            fontSize = 23.sp,
                            color = Color(0xFF4BEB06),
                            modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.padding_larger))
                                .weight(0.35f),
                            onClick = onOnlineButtonClicked
                        )
                        Spacer(Modifier.weight(0.125f))
                        CustomButton(
                            text ="Play Offline",
                            fontSize = 23.sp,
                            color = Color(0xFFE82E2E),
                            modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.padding_larger))
                                .weight(0.35f),
                            onClick = {}
                        )
                        Spacer(Modifier.weight(0.125f))
                        CustomButton(
                            text = "Score Board",
                            fontSize = 23.sp,
                            color = Color(0xFFFFB700),
                            modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.padding_larger))
                                .weight(0.35f),
                            onClick = {}
                        )
                        Spacer(Modifier.weight(0.15f))
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ChallengeScreenPreview() {
    ChallengeScreen(
        onOnlineButtonClicked = {}
    )
}