package com.example.android.kotlincoroutines.main.channels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.delay

class ChannelViewModel : ViewModel() {

    private val rep = TestProducerRepository()

    suspend fun getValuesChannel(): ReceiveChannel<String> {
        return transformProduce(rep.produce(viewModelScope)) {
            it.toUpperCase()
        }
    }

    private suspend fun transformProduce(values: ReceiveChannel<String>, rule: (String) -> String): ReceiveChannel<String> {
        return viewModelScope.produce {
            for (value in values) {
                send(value)
                delay(500)
                send(" [TRANSFORMED] " + rule(value))
            }
        }
    }
}