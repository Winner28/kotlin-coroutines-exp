package com.example.android.kotlincoroutines.main.channels

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce

interface Producer<T> {
    suspend fun produce(scope: CoroutineScope): ReceiveChannel<T>
}

class TestProducerRepository: Producer<String> {

    private var value = 0

    override suspend fun produce(scope: CoroutineScope): ReceiveChannel<String> {
        return scope.produce {
            while (value < 1000) {
                delay(1000)
                send("Produced value: ${++value}")
                delay(1000)
                send("...delay...")
            }
        }
    }
}