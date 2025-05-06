package com.example.engmas.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Topic(
    @SerialName(value = "topic_id")
    val topicId: Int = -1,
    @SerialName(value = "topic_name")
    val topicName: String = "",
    @SerialName(value = "topic_name_vi")
    val topicNameVi: String = "",
)
