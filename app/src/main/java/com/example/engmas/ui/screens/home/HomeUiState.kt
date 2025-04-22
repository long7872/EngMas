package com.example.engmas.ui.screens.home

import com.example.engmas.data.model.Vocab

data class HomeUiState(
    val query: String = "",
    val searchResults: List<Vocab> = listOf()
)