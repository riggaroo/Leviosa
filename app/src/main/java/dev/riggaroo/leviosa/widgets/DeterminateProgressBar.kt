package dev.riggaroo.leviosa.widgets


import androidx.compose.animation.animatedFloat
import androidx.compose.animation.core.AnimationConstants
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.onActive
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import dev.riggaroo.leviosa.ui.purple200
import dev.riggaroo.leviosa.ui.purple500
import dev.riggaroo.leviosa.ui.purple700
import kotlin.math.roundToInt

@Composable
fun DeterminateProgressBar(modifier: Modifier = Modifier,
                           progress: Float,
                           progressColors: List<Color> = listOf(purple200, purple500, purple700),
                           backgroundColor: Color = MaterialTheme.colors.onSurface.copy(alpha = 0.2f),
                           textStyle: TextStyle = MaterialTheme.typography.h5,
                           textColor: Color = MaterialTheme.colors.onBackground) {

    val drawStyle = remember { Stroke(width = 16.dp.value, cap = StrokeCap.Round) }
    val brush = remember {
        if (progressColors.size == 1){
            SolidColor(progressColors[0])
        } else {
            HorizontalGradient(progressColors, 0f,  200f, TileMode.Mirror)
        }
    }

    val brushBackground = remember { SolidColor(backgroundColor) }
    val animateCurrentProgress = animatedFloat(progress)
    animateCurrentProgress.animateTo(progress, anim = tween(easing = FastOutSlowInEasing))

    // Animation for the rotation of the whole progress control.
    val overallAnimation = animatedFloat(0f)
    onActive {
        overallAnimation.animateTo(
                targetValue = 1f,
                anim = repeatable(
                        repeatMode = RepeatMode.Restart,
                        iterations = AnimationConstants.Infinite,
                        animation = tween(durationMillis = 2000, easing = LinearEasing),
                ),
        )
    }

    val rotationOffsetAngle = overallAnimation.value * PROGRESS_FULL_DEGREES
    val progressDegrees = animateCurrentProgress.value * PROGRESS_FULL_DEGREES

    Box {
        Canvas(modifier = modifier.then(Modifier.padding(16.dp))) {
            // Background of Progress bar
            drawArc(brush = brushBackground,
                    startAngle = 0f,
                    sweepAngle = PROGRESS_FULL_DEGREES,
                    useCenter = false,
                    style = drawStyle)
            // Rotate the whole canvas to move the whole animation to show movement even when the progress doesn't change
            rotate(rotationOffsetAngle) {
                // Foreground of progress bar, only drawn to the size of the progress
                drawArc(brush = brush,
                        startAngle = 0f,
                        sweepAngle = progressDegrees,
                        useCenter = false,
                        style = drawStyle)
            }
        }
        // Text progress indicator in the middle of the Progress bar.
        Box(modifier = Modifier.align(Alignment.Center)) {
            Text("${(animateCurrentProgress.value * 100f).roundToInt()}%",
                    color = textColor,
                    textAlign = textStyle.textAlign,
                    fontSize = textStyle.fontSize)
        }
    }
}

private const val PROGRESS_FULL_DEGREES = 360f