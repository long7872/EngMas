package com.example.engmas.ui.screens.account.data

import com.example.engmas.data.model.UserFriendStatus
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Friend(
    @SerialName(value = "friend_id")
    val friendId: String = "",
    val username: String = "",
    val email: String = "",
    @SerialName(value = "photo_url")
    val photoUrl: String = "",
    val status: UserFriendStatus = UserFriendStatus.None,
)
