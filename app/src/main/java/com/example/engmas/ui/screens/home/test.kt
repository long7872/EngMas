package com.example.engmas.ui.screens.home

import java.util.Calendar
import java.util.Locale

fun getStartAndEndOfCurrentWeek(date: Calendar): Pair<String, String> {
    // Đặt ngày là ngày hiện tại
    val startOfWeek = date.clone() as Calendar
    startOfWeek.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY) // Thứ hai là ngày bắt đầu tuần

    val endOfWeek = startOfWeek.clone() as Calendar
    endOfWeek.add(Calendar.DAY_OF_YEAR, 6)  // Thứ bảy là ngày cuối tuần

    // Trả về dạng "dd/MM/yyyy"
    val startDate = formatDate(startOfWeek)
    val endDate = formatDate(endOfWeek)

    return Pair(startDate, endDate)
}

fun getPreviousWeek(date: Calendar): Pair<String, String> {
    // Lùi về một tuần để lấy tuần trước
    date.add(Calendar.WEEK_OF_YEAR, -1)
    return getStartAndEndOfCurrentWeek(date)
}

fun formatDate(calendar: Calendar): String {
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    val month = calendar.get(Calendar.MONTH) + 1  // Lưu ý, tháng bắt đầu từ 0 (0 = January)
    val year = calendar.get(Calendar.YEAR)

    // Sử dụng Locale.getDefault() hoặc Locale("en", "US") để đảm bảo kết quả không bị ảnh hưởng bởi cấu hình khu vực
    return String.format(Locale.getDefault(), "%02d/%02d/%04d", day, month, year)  // Định dạng "dd/MM/yyyy"
}

fun main() {
    val today = Calendar.getInstance()
    val (startOfCurrentWeek, endOfCurrentWeek) = getStartAndEndOfCurrentWeek(today)
    val (startOfPreviousWeek, endOfPreviousWeek) = getPreviousWeek(today)

    val oneWeekAgo = today.clone() as Calendar
    oneWeekAgo.add(Calendar.WEEK_OF_YEAR, -1)
    val (startOf1CurrentWeek, endOf1CurrentWeek) = getStartAndEndOfCurrentWeek(oneWeekAgo)
    val (startOf1PreviousWeek, endOf1PreviousWeek) = getPreviousWeek(oneWeekAgo)

    val twoWeekAgo = today.clone() as Calendar
    twoWeekAgo.add(Calendar.WEEK_OF_YEAR, -2)
    val (startOf2CurrentWeek, endOf2CurrentWeek) = getStartAndEndOfCurrentWeek(twoWeekAgo)
    val (startOf2PreviousWeek, endOf2PreviousWeek) = getPreviousWeek(twoWeekAgo)

    println("Current Week Start: $startOfCurrentWeek, End: $endOfCurrentWeek")
    println("Previous Week Start: $startOfPreviousWeek, End: $endOfPreviousWeek")
    println("Current 1 Week Start: $startOf1CurrentWeek, End: $endOf1CurrentWeek")
    println("Previous 1 Week Start: $startOf1PreviousWeek, End: $endOf1PreviousWeek")
    println("Current 2 Week Start: $startOf2CurrentWeek, End: $endOf2CurrentWeek")
    println("Previous 2 Week Start: $startOf2PreviousWeek, End: $endOf2PreviousWeek")

    if (startOf1PreviousWeek == startOf2CurrentWeek && endOf1PreviousWeek == endOf2CurrentWeek)
        println("Previous 1 Week == Current 2 Week Start")
}
