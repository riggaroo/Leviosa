package dev.riggaroo.leviosa

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import dev.riggaroo.leviosa.ui.LeviosaTheme
import dev.riggaroo.leviosa.widgets.DeterminateProgressBar

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<ProgressViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LeviosaTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    ProgressScreen(progressViewModel = viewModel)
                }
            }
        }
        viewModel.startProgress()
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    LeviosaTheme {
        DeterminateProgressBar(
                modifier = Modifier.size(200.dp, 200.dp),
                progress = 0.2f)
    }
}

@Preview()
@Composable
fun ProgressDarkTheme() {
    LeviosaTheme(darkTheme = true) {
        DeterminateProgressBar(
                modifier = Modifier.size(200.dp, 200.dp),
                progressColors = listOf(Color.Magenta, Color.Red),
                progress = 0.5f)
    }
}


@Composable
fun ProgressScreen(progressViewModel: ProgressViewModel) {
    DeterminateProgressBar(modifier = Modifier.size(200.dp, 200.dp),
            progress = progressViewModel.progress)
}
