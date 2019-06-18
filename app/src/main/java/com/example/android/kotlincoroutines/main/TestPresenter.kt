package com.example.android.kotlincoroutines.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class TestPresenter(var view: View?, val rep: TestRepository) {

    private val _data = MutableLiveData<String>()
    private val _progressing = MutableLiveData<Boolean>()

    val data: LiveData<String> get() = _data
    val progressing: LiveData<Boolean> get() = _progressing

    suspend fun onShow() {
        _progressing.value = true
        _data.value = rep.getValue()
        _progressing.value = false
    }
}