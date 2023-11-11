package com.example.ejemploshiloskotlin


import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var tvCounter: TextView
    private lateinit var btnStart: Button
    private lateinit var btnStop: Button
    private lateinit var btnResume: Button
    private lateinit var btnReset: Button
    private var counterTask: CounterTask? = null
    private var count = 0
    private var isRunning = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvCounter = findViewById(R.id.tvCounter)
        btnStart = findViewById(R.id.btnStart)
        btnStop = findViewById(R.id.btnStop)
        btnResume = findViewById(R.id.btnResume)
        btnReset = findViewById(R.id.btnReset)

        btnStart.setOnClickListener {
            if (!isRunning) {
                counterTask = CounterTask()
                counterTask?.execute(count)
            }
        }

        btnStop.setOnClickListener {
            if (isRunning) {
                counterTask?.cancel(true)
                isRunning = false
            }
        }

        btnResume.setOnClickListener {
            if (!isRunning) {
                counterTask = CounterTask()
                counterTask?.execute(count)
            }
        }

        btnReset.setOnClickListener {
            count = 0
            tvCounter.text = count.toString()
        }
    }

    private inner class CounterTask : AsyncTask<Int, Int, Void?>() {
        override fun onPreExecute() {
            super.onPreExecute()
            isRunning = true
        }

        override fun doInBackground(vararg integers: Int?): Void? {
            var start = integers[0] ?: 0
            while (!isCancelled) {
                try {
                    Thread.sleep(1000)
                    publishProgress(++start)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
            return null
        }

        override fun onProgressUpdate(vararg values: Int?) {
            super.onProgressUpdate(*values)
            count = values[0] ?: 0
            tvCounter.text = count.toString()
        }

        override fun onCancelled() {
            super.onCancelled()
            isRunning = false
        }
    }
}
