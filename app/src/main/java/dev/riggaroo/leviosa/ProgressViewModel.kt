package dev.riggaroo.leviosa

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class ProgressViewModel : ViewModel() {

    var progress: Float by mutableStateOf<Float>(0f)
        private set

    init {
        progress = 0f
    }

    /**
     * Dummy method that simulates progress from 0.0f to 1.0f
     */
    fun startProgress() {
        progress = 0f
        GlobalScope.launch(Dispatchers.Main) {
            val totalSeconds = TimeUnit.MINUTES.toSeconds(100)
            val tickSeconds = 1
            for (second in totalSeconds downTo tickSeconds) {
                if (progress > 1.0f) break
                progress += 0.01f
                Log.v("ProgressViewModel", "progress $progress")
                delay(100)
            }
            progress = 1.0f
        }
    }
}