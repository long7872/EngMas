package com.example.engmas.data.repository

import android.util.Log
import com.example.engmas.data.model.Course
import com.example.engmas.network.EngMasApiService
import com.example.engmas.ui.screens.home.data.CourseLearning
import com.example.engmas.ui.screens.practice.courses.data.QuestionInCourse
import com.example.engmas.ui.screens.practice.courses.data.UpdateStatusRequest

interface CourseRepository {
    suspend fun getAllCourses(): List<Course>
    suspend fun getCourseLearning(courseId: Int, userId: String): QuestionInCourse
    suspend fun syncStatus(request: UpdateStatusRequest): String
    suspend fun getUserCourse(userId: String): List<CourseLearning>
}

class NetworkCourseRepository(private val api: EngMasApiService): CourseRepository {

    override suspend fun getAllCourses(): List<Course> {
        val response = api.getAllCourses()
        if (response.isSuccessful) {
            val courses = response.body()
            if (courses != null) return courses
            else throw Exception("Empty user list")
        } else {
            throw Exception("Error: ${response.code()} ${response.message()}")
        }
    }

    override suspend fun getCourseLearning(courseId: Int, userId: String): QuestionInCourse {
        Log.d("Course Repository", "function getCourseLearning: Parameters: $courseId, $userId")
        val response = api.getCourseLearning(courseId, userId)
        if (response.isSuccessful) {
            val course = response.body()
            if (course != null) return course
            else throw Exception("Not found course")
        } else {
            throw Exception("Error: ${response.code()} ${response.message()}")
        }
    }

    override suspend fun syncStatus(request: UpdateStatusRequest): String {
        Log.d("Course Repository", "function syncStatus: Parameters: $request")
        val response = api.updatesUserLearningStatus(request)
        if (response.isSuccessful) {
            val info = response.body()?.string()
            if (info != null) return info
            else throw Exception("sync status failed")
        } else {
            throw Exception("Error: ${response.code()} ${response.message()}")
        }
    }

    override suspend fun getUserCourse(userId: String): List<CourseLearning> {
        Log.d("Course Repository", "function getUserCourse: Parameters: $userId")
        val response = api.getUserCourses(userId)
        return if (response.isSuccessful) {
            response.body() ?: throw Exception("sync status failed")
        } else if (response.code() == 404) {
            emptyList()
        } else {
            throw Exception("Error: ${response.code()} ${response.message()}")
        }
    }
}
