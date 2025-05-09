package com.example.engmas.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class UserCourse(
    @SerialName(value = "user_id")
    val userId: String = "",
    @SerialName(value = "course_id")
    val courseId: Int = -1,
    val status: UserCourseStatus = UserCourseStatus.InProgress,
    val progress : Float = 0f,
    @Transient
    val timestamp: String = "",
)

enum class UserCourseStatus {
    InProgress,
    Done
}
