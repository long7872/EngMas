package com.example.engmas.ui.screens.home

import com.example.engmas.data.model.UserScore
import com.example.engmas.data.model.Vocab
import com.example.engmas.ui.screens.home.data.CourseLearning
import com.example.engmas.ui.screens.home.data.VocabInfo

data class HomeUiState(
    val query: String = "",
    val searchResults: List<Vocab> = listOf(),
    val userScore: UserScore = UserScore(),
    val courseList: List<CourseLearning> = emptyList(),
)