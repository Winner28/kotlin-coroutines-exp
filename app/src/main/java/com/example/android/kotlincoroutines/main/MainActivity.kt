package com.example.android.kotlincoroutines.main

import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.viewModelScope
import com.example.android.kotlincoroutines.main.channels.ChannelViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.consumeEach

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val textView = TextView(this).apply {
            text = "textview"
            gravity = Gravity.CENTER
            layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        }

        ViewModelProviders.of(this).get(ChannelViewModel::class.java).apply {
           viewModelScope.launch {
               getValuesChannel().consumeEach {
                   textView.text = it
               }
           }
        }

        setContentView(LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            addView(textView)
        })
    }
}
