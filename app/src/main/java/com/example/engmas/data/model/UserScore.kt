package com.example.engmas.data.model

import com.google.firebase.firestore.Exclude

data class UserScore(
    val id: String = "",
    val name: String = "",
    val score: Int = 0,
    val streak: Streak = Streak(),
    val currentWeekStats: Stats = Stats(),
    val previousWeekStats: Stats = Stats(),

    @get:Exclude
    @set:Exclude
    var rank: Int = 0
)

data class Streak(
    val completedDays: List<String> = emptyList(),
    val highestStreak: Int = 0,
    val lastUpdated: String = "",
    val today: Today = Today(),
    val nowStreak: Int = 0
)

data class Today(
    val day: String = getCurrentDayOfWeek(),
    val completed: Boolean = false
)

data class Stats(
    val wordsLearned: Int = 0,
    val wordsToReview: Int = 0,
    val weekStartDate: String = "",
    val weekEndDate: String = ""
)

private fun getCurrentDayOfWeek(): String {
    val days = listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat") // Thứ tự của Calendar
    val calendar = java.util.Calendar.getInstance()
    val dayIndex = calendar.get(java.util.Calendar.DAY_OF_WEEK) - 1 // Calendar: Sunday=1
    return days[dayIndex]
}