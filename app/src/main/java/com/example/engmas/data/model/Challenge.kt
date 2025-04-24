package com.example.engmas.data.model

import com.google.firebase.Timestamp

data class Challenge(
    val challengeId: String = "",
    val wordList: List<String> = listOf(),
    val scrambleWordList: List<String> = listOf(),
    val player1Id: String = "",
    val player2Id: String = "",
    val player1Progress: Int = 0,
    val player2Progress: Int = 0,
    val player1Score: Int = 0,
    val player2Score: Int = 0,
    val player1Status: String = "",
    val player2Status: String = "",
    val createdAt: Timestamp = Timestamp.now()
)
