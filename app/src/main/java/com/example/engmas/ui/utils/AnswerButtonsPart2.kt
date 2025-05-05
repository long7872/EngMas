package com.example.engmas.ui.utils

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.engmas.ui.theme.EngMasTheme
import com.example.engmas.ui.theme.KufamFont

@Composable
fun AnswerButtonsPart2(modifier: Modifier = Modifier) {
    // Mutable state to track the selected button
    var selectedAnswer by remember { mutableStateOf<String?>(null) }

    // Button color based on selection state
    val buttonColor = Color(0xFF8FE8FA) // Light blue (used for selected buttons)

    // Function to determine the button color based on whether it is selected
    fun getButtonColor(answer: String): Color {
        return if (selectedAnswer == answer) buttonColor else Color(0xFFE3F2FD) // Light blue
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        modifier = modifier
    ) {
        // Option A
        Button(
            onClick = { selectedAnswer = "A" },
            modifier = Modifier.size(350.dp, 60.dp),
            colors = ButtonDefaults.buttonColors(containerColor = getButtonColor("A")),
            border = BorderStroke(2.dp, Color(0xFFD4D2D2)),
            elevation = ButtonDefaults.buttonElevation(2.dp),
            shape = MaterialTheme.shapes.small,
        ) {
            Text(
                text = "A",
                fontSize = 20.sp,
                color = Color(0xFF757575),
                fontFamily = KufamFont,
                fontWeight = FontWeight.Bold
            )
        }

        // Option B
        Button(
            onClick = { selectedAnswer = "B" },
            modifier = Modifier.size(350.dp, 60.dp),
            colors = ButtonDefaults.buttonColors(containerColor = getButtonColor("B")),
            border = BorderStroke(2.dp, Color(0xFFD4D2D2)),
            elevation = ButtonDefaults.buttonElevation(2.dp),
            shape = MaterialTheme.shapes.small,
        ) {
            Text(
                text = "B",
                fontSize = 20.sp,
                color = Color(0xFF757575),
                fontFamily = KufamFont,
                fontWeight = FontWeight.Bold
            )
        }
        // Option C
        Button(
            onClick = { selectedAnswer = "C" },
            modifier = Modifier.size(350.dp, 60.dp),
            colors = ButtonDefaults.buttonColors(containerColor = getButtonColor("C")),
            border = BorderStroke(2.dp, Color(0xFFD4D2D2)),
            elevation = ButtonDefaults.buttonElevation(2.dp),
            shape = MaterialTheme.shapes.small,
        ) {
            Text(
                text = "C",
                fontSize = 20.sp,
                color = Color(0xFF757575),
                fontFamily = KufamFont,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AnswerButtonsPart2Preview() {
    EngMasTheme {
        AnswerButtonsPart2(modifier = Modifier.padding(start = 50.dp, end = 50.dp))
    }
}