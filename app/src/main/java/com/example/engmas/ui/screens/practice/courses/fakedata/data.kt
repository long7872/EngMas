package com.example.engmas.ui.screens.practice.vocabulary.fakedata

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color

data class VocabularyItem(
    val containerColor: Color,
    val itemColor: Color,
    @DrawableRes val iconRes: Int,
    @StringRes val iconDes: Int,
    @StringRes val text: Int,
    val onClick: () -> Unit
)
