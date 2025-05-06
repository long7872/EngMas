package com.example.engmas.ui.utils

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.engmas.ui.theme.EngMasTheme
import com.example.engmas.ui.theme.KufamFont

@Composable
fun Reading(reading: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        // Reading
        Text(
            text = reading,
            fontFamily = KufamFont,
            fontWeight = FontWeight.SemiBold,
            fontSize = 15.sp,
            color = Color(0xFF757575),
            modifier = Modifier.padding(bottom = 6.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ReadingPreview() {
    EngMasTheme {
        Reading(reading = "71-73 M-Au (71) You have reached the information line for the Cranbury Apartments management office. On Monday, April twelfth, maintenance work will begin to repave the entire parking area adjacent to our building's main entrance. (72) All Cranbury residents should move their vehicles from their designated parking spots before eight a.m. on Monday. Any vehicle still in its spot after eight a.m. will be towed at the owner's expense. (73) A map of alternate parking sites was mailed to residents last week and is also posted in the building lobby.")
    }
}
