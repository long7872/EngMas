package com.example.engmas.ui.screens.home.data

import com.example.engmas.data.model.CourseType
import com.example.engmas.data.model.UserCourseStatus
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class CourseLearning(
    @SerialName(value = "user_id")
    val userId: String = "",
    @SerialName(value = "course_id")
    val courseId: Int = -1,
    val status: UserCourseStatus = UserCourseStatus.InProgress,
    val progress : Float = 0f,
    @Transient
    val timestamp: String = "",
    @SerialName(value = "course_name")
    val name: String = "",
    val type: CourseType = CourseType.Beginner,
)
