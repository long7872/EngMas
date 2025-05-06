package com.example.engmas.ui.utils

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.engmas.ui.theme.KufamFont

@Composable
fun Previous_Next_Button() {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp)
    ) {
        OutlinedButton(
            onClick = {},
            border = BorderStroke(1.dp, Color(0xFFD3D3D3)),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .padding(20.dp)
                .size(130.dp, 40.dp)
        ) {
            Text(
                text = "Previous",
                color = Color(0xFF757575),
                fontFamily = KufamFont,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
            )
        }

        Button(
            onClick = {},
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF24D3E3)) ,
            modifier = Modifier
                .padding(20.dp)
                .size(130.dp, 40.dp)
        ) {
            Text(
                text = "Next",
                color = Color(0xFFFFFFFF),
                fontFamily = KufamFont,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
            )
        }
    }
}