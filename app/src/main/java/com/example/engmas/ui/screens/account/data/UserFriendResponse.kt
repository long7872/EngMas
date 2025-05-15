package com.example.engmas.ui.screens.account.data

import com.example.engmas.data.model.UserFriendStatus
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class UserFriendResponse(
    @SerialName(value = "u_f_id")
    val id: Int = -1,
    @SerialName(value = "user_id")
    val userId: String = "",
    @SerialName(value = "friend_id")
    val friendId: String = "",
    @SerialName(value = "friend_name")
    val friendName: String = "",
    @SerialName(value = "friend_photo")
    val friendPhoto: String = "",
    val status: UserFriendStatus = UserFriendStatus.Pending,
    val sender: String = "",
    val timestamp: String = "",
)