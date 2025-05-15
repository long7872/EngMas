package com.example.engmas.ui.screens.account.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LearningBadge(
    @SerialName(value = "user_id")
    val userId: String = "",
    @SerialName(value = "badge_id")
    val badgeId: Int = -1,
    val quantity: Int = -1,
    val favourite: Int = -1,
    @SerialName(value = "badge_type")
    val badgeType: String = "",
    @SerialName(value = "image_path")
    val imagePath: String = "",
)
