package com.example.engmas.ui.utils

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

enum class BorderSide { Top, Bottom, Left, Right }

fun Modifier.customBorder(
    width: Dp,
    color: Color,
    sides: Set<BorderSide> = setOf(BorderSide.Top, BorderSide.Bottom, BorderSide.Left, BorderSide.Right)
) = this.then(
    Modifier.drawBehind {
        val strokeWidth = width.toPx()

        if (BorderSide.Top in sides) {
            drawLine(color, Offset(0f, 0f), Offset(size.width, 0f), strokeWidth)
        }
        if (BorderSide.Bottom in sides) {
            drawLine(color, Offset(0f, size.height), Offset(size.width, size.height), strokeWidth)
        }
        if (BorderSide.Left in sides) {
            drawLine(color, Offset(0f, 0f), Offset(0f, size.height), strokeWidth)
        }
        if (BorderSide.Right in sides) {
            drawLine(color, Offset(size.width, 0f), Offset(size.width, size.height), strokeWidth)
        }
    }
)