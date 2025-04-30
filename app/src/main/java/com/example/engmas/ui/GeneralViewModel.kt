package com.example.engmas.ui

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.ProcessLifecycleOwner
import com.example.engmas.data.store.SessionPreferences
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class GeneralViewModel : ViewModel() {

    companion object {
        private const val TARGET_MILLIS = 30_000L // 10 phút 600_000
    }

    private val _elapsedMillis = MutableStateFlow(0L)
    val elapsedMillis: StateFlow<Long> = _elapsedMillis

    private val _remainingMillis = MutableStateFlow(TARGET_MILLIS)
    val remainingMillis: StateFlow<Long> = _remainingMillis

    private var isTracking = false
    private var lastResumeTime = 0L

    private fun getToday(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return sdf.format(Date())
    }

    fun startSessionTracking(context: Context, onCompleted: () -> Unit) {
        viewModelScope.launch {
            val today = getToday()

            if (SessionPreferences.getLastTrackedDay(context) == null) {
                SessionPreferences.saveLastTrackedDay(context, today)
            }
            val lastTrackedDay = SessionPreferences.getLastTrackedDay(context)
            val isCompletedToday = SessionPreferences.isTodayCompleted(context)

            Log.d("Timer", "get 2 ${SessionPreferences.getElapsedMillis(context)} " +
                    "${SessionPreferences.isTodayCompleted(context)} + " +
                    "${SessionPreferences.getLastTrackedDay(context)} $today")

            if (lastTrackedDay != today) {
                // Ngày mới ➔ clear hết
                SessionPreferences.clear(context)
                _elapsedMillis.value = 0L
                _remainingMillis.value = TARGET_MILLIS
                Log.d("Timer", "Clear")
            } else {
                // Ngày cũ ➔ lấy dữ liệu cũ
                _elapsedMillis.value = SessionPreferences.getElapsedMillis(context)
                _remainingMillis.value = (TARGET_MILLIS - _elapsedMillis.value).coerceAtLeast(0L)

                if (isCompletedToday) {
                    // Nếu đã complete rồi thì không tracking nữa
                    return@launch
                }
            }

            ProcessLifecycleOwner.get().lifecycle.addObserver(object : androidx.lifecycle.DefaultLifecycleObserver {
                override fun onStart(owner: androidx.lifecycle.LifecycleOwner) {
                    resumeTracking(context, onCompleted)
                }

                override fun onStop(owner: androidx.lifecycle.LifecycleOwner) {
                    pauseTracking(context)
                }
            })
        }
    }

    private fun resumeTracking(context: Context, onCompleted: () -> Unit) {
        if (isTracking || _elapsedMillis.value >= TARGET_MILLIS) return
        isTracking = true
        lastResumeTime = System.currentTimeMillis()

        viewModelScope.launch {
            while (isTracking) {
                delay(1000L)
                val now = System.currentTimeMillis()
                val delta = now - lastResumeTime
                lastResumeTime = now

                _elapsedMillis.value += delta
                _elapsedMillis.value = _elapsedMillis.value.coerceAtMost(TARGET_MILLIS)
                _remainingMillis.value = (TARGET_MILLIS - _elapsedMillis.value).coerceAtLeast(0L)

                // Cứ mỗi giây update vào DataStore
                SessionPreferences.saveElapsedMillis(context, _elapsedMillis.value)

                if (_elapsedMillis.value >= TARGET_MILLIS) {
                    isTracking = false
                    SessionPreferences.saveTrackingCompleted(context, getToday())
                    onCompleted()
                }
                Log.d("Timer", "${SessionPreferences.getElapsedMillis(context)}")
            }
        }
    }

    private fun pauseTracking(context: Context) {
        if (!isTracking) return
        val now = System.currentTimeMillis()
        val delta = now - lastResumeTime

        _elapsedMillis.value += delta
        _elapsedMillis.value = _elapsedMillis.value.coerceAtMost(TARGET_MILLIS)
        _remainingMillis.value = (TARGET_MILLIS - _elapsedMillis.value).coerceAtLeast(0L)

        viewModelScope.launch {
            SessionPreferences.saveElapsedMillis(context, _elapsedMillis.value)
        }

        isTracking = false
    }
}