package com.example.engmas.ui.screens.challenge.online

data class Challenge_OnlineUiState(
    val matchingState: MatchingState = MatchingState.Matching,
    val resultState: ResultState = ResultState.None
)

enum class MatchingState {
    Matching,    // In the process of matching
    Matched      // Successfully matched
}
enum class ResultState {
    None,
    Win,    // In the process of matching
    Lose      // Successfully matched
}