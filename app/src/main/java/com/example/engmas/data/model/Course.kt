package com.example.engmas.data.model

import com.example.engmas.R
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class Course(
    @SerialName(value = "course_id")
    val id: Int = -1,
    @SerialName(value = "course_name")
    val name: String = "",
    val type: CourseType = CourseType.Beginner
)

enum class CourseType {
    Beginner,
    Intermediate,
    Advance
}
