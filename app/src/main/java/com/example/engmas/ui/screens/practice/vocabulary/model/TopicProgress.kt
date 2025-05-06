package com.example.engmas.ui.screens.practice.vocabulary.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TopicProgress(
    @SerialName(value = "topic_id")
    val id: Int,
    @SerialName(value = "topic_name")
    val topicName: String,
    @SerialName(value = "topic_name_vi")
    val topicNameVi: String,
    val progress: Float,
)

