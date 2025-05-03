package com.example.engmas.ui.screens.practice.courses.fakedata

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color

data class VocabularyItem(
    val containerColor: Color,
    val itemColor: Color,
    @DrawableRes val iconRes: Int,
    @StringRes val iconDes: Int,
    @StringRes val text: Int,
    val onClick: () -> Unit,
    val progress: Float
)
