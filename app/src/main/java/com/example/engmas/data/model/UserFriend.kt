package com.example.engmas.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class UserFriend(
    @SerialName(value = "u_f_id")
    val id: Int = -1,
    @SerialName(value = "user_id")
    val userId: String = "",
    @SerialName(value = "friend_id")
    val friendId: String = "",
    val status: UserFriendStatus = UserFriendStatus.None,
    val timestamp: String = "",
)

enum class UserFriendStatus {
    None,
    Pending,
    Accepted,
    Blocked
}