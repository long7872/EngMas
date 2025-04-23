package com.example.engmas.data.model

data class UserScore(
    val id: String = "",
    val name: String = "",
    val score: Int = 0,
    val rank: Int = 0 // Thêm thuộc tính rank
)
