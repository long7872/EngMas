package com.example.engmas.data.model

import com.google.firebase.Timestamp

data class Challenge(
    val challengeId: String = "",
    val player1Id: String = "",
    val player2Id: String = "",
    val player1Progress: Int = 0,
    val player2Progress: Int = 0,
    val createdAt: Timestamp = Timestamp.now()
)