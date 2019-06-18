package com.example.android.kotlincoroutines.main

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.delay

class CustomView(context: Context) : android.view.View(context) {

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawCircle(50f, 50f, 40f, Paint().apply {
            color = Color.RED
        })
    }
}

class CreateViewViewModel : ViewModel() {
    private val creator = ViewCreator()

    suspend fun createView(context: Context): android.view.View {
        return creator.createView(context)
    }


    private inner class ViewCreator {
        suspend fun createView(context: Context): android.view.View {
            delay(4000)
            return CustomView(context)
        }
    }
}