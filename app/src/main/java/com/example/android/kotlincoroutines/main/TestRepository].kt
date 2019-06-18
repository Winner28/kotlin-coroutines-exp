package com.example.android.kotlincoroutines.main

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class TestRepository {
    companion object {
        var i = 0
    }
    suspend fun getValue(): String {
        return withContext(Dispatchers.IO) {
            delay(1500)
            "[keep clicking] Hello from suspend function ${i++}"
        }
    }
}