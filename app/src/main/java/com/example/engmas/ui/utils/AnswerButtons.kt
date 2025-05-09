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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.engmas.ui.theme.EngMasTheme
import com.example.engmas.ui.theme.KufamFont

@Composable
fun AnswerButtons(
    modifier: Modifier = Modifier,
    selectedAnswer: String?, // Truyền selectedAnswer từ bên ngoài vào
    options: List<String>,
    onAnswerSelected: (String) -> Unit
) {
    // Button color based on selection state
    val buttonColor = Color(0xFF8FE8FA) // Light blue (used for selected buttons)

    // Function to determine the button color based on whether it is selected
    fun getButtonColor(answer: String): Color {
        return if (selectedAnswer == answer) buttonColor else Color(0xFFE3F2FD) // Light blue
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = modifier
    ) {
        options.forEachIndexed { index, option ->
            Button(
                onClick = {
                    onAnswerSelected(option) // Gọi hàm callback khi người dùng chọn đáp án
                },
                modifier = Modifier.size(350.dp, 60.dp),
                colors = ButtonDefaults.buttonColors(containerColor = if (selectedAnswer == option) Color(0xFF8FE8FA) else Color(0xFFE3F2FD)),
                border = BorderStroke(2.dp, Color(0xFFD4D2D2)),
                elevation = ButtonDefaults.buttonElevation(2.dp),
                shape = MaterialTheme.shapes.small,
            ) {
                Text(
                    text = option,
                    fontSize = 20.sp,
                    color = Color(0xFF757575),
                    fontFamily = KufamFont,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AnswerButtonsPreview() {
    EngMasTheme {
        val options = listOf("A", "B", "C", "D") // Chỉ hiển thị các nút A, B, C, D
        AnswerButtons(
            modifier = Modifier.padding(start = 50.dp, end = 50.dp),
            selectedAnswer = null,
            options = options,
            onAnswerSelected = { answer ->
                println("Selected answer: $answer")
            }
        )
    }
}
