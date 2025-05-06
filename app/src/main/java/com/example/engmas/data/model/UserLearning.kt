package com.example.engmas.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import java.sql.Timestamp

@Serializable
data class UserLearning(
    @SerialName(value = "u_l_id")
    @Transient
    val id: Int = -1,
    @SerialName(value = "user_id")
    val userId: String = "",
    @SerialName(value = "api_id")
    val apiId: Int = -1,
    val status: UserLearningStatus = UserLearningStatus.Learning,
    @Transient
    val timestamp: String = "",
)

enum class UserLearningStatus {
    Learning,
    Review,
    Known
}
