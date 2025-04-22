package com.example.engmas.ui.screens.challenge.online

import com.example.engmas.data.model.User

data class Challenge_OnlineUiState(
    val matchingState: MatchingState = MatchingState.Matching,
    val resultState: ResultState = ResultState.None,
    val matchedUser: User = User(),
    val originalList: List<String> = emptyList(),
    val unscrambleList: List<String> = emptyList(),
    val thisUserCurrentQuestion: Int = 0,
    val otherUserCurrentQuestion: Int = 0
)

enum class MatchingState {
    Matching,    // In the process of matching
    Matched,      // Successfully matched
    Play,
}
enum class ResultState {
    None,
    Win,    // In the process of matching
    Lose      // Successfully matched
}