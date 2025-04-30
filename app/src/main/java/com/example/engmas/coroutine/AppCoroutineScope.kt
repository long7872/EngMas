package com.example.engmas.coroutine

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

object AppCoroutineScope {
    private val job = SupervisorJob()
    val scope = CoroutineScope(Dispatchers.IO + job)
}
