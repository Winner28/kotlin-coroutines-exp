package com.example.android.kotlincoroutines.main

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    private var i = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val params = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

        val textView = TextView(this).apply {
            text = "CLICK ON ME"
            layoutParams = params
        }

        val second = TextView(this).apply {
            text = "Red circle appears in 3.."
            layoutParams = params
        }

        val spinner = ProgressBar(this).apply { visibility = android.view.View.GONE }

        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            addView(textView)
            addView(second)
            addView(spinner)
        }

        ViewModelProviders.of(this).get(CreateViewViewModel::class.java).apply {
            viewModelScope.launch {
                layout.addView(createView(this@MainActivity))
            }
        }

        ViewModelProviders.of(this).get(MainViewModel::class.java).apply {
//            presenter.view = object : View {
//                override suspend fun showValue(value: String) {
//                    textView.text = value
//                }
//            }

            // NOTE: create presenter.view or observe presenter.data

            presenter.data.observe(this@MainActivity, Observer {
                textView.text = it
            })

            presenter.progressing.observe(this@MainActivity, Observer { isProgress ->
                spinner.visibility = if (isProgress) android.view.View.VISIBLE else android.view.View.GONE
            })

            textView.setOnClickListener {
                second.text = "thx ${++i}"
                spinner.visibility = android.view.View.VISIBLE
                viewModelScope.launch {
                    presenter.onShow(i.toString())
                }
            }
        }

        setContentView(layout)
    }
}

interface View {
    suspend fun showValue(value: String)
}
