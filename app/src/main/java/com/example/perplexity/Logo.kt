package com.example.perplexity

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.HazeTint
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.hazeSource

@Composable
fun PerplexityLogo(
    sliderValue: Float,
    modifier: Modifier = Modifier
) {


    val hazeState = remember { HazeState() }

    val containerColor = MaterialTheme.colorScheme.surface
    val lightAlpha = 0.3f
    val darkAlpha = 0.1f
    val hazeStyle = HazeStyle(
        backgroundColor = containerColor,
        tints = listOf(
            HazeTint(
                containerColor.copy(alpha = if (containerColor.luminance() >= 0.5) lightAlpha else darkAlpha),
            )
        ),
        blurRadius = 1.dp,
        noiseFactor = 0.1f,
        fallbackTint = HazeTint.Unspecified,
    )

//    val infiniteTransition = rememberInfiniteTransition()
//    val animationProgress by infiniteTransition.animateFloat(
//        initialValue = 1f,
//        targetValue = 0f,
//        animationSpec = infiniteRepeatable(
//            animation = tween(5_000),
//            repeatMode = RepeatMode.Restart
//        )
//    )
    val animationProgress = sliderValue
    Box(
        contentAlignment = Alignment.Center,
    ) {
        val size = 3
        MutableList(size) { index ->
            val rotationAngle = (animationProgress * 360 + (index * 360 / size)) % 360
            //val rotationAngle = (0.5f * 360 + (index * 360 / size)) % 360
            val zIndex =
                (if (rotationAngle <= 90) 90 - rotationAngle else rotationAngle - 90).let {
                    if (it > 180) 360 - it else it
                }
            RoundedBox(
                size,
                zIndex,
                rotationAngle,
                hazeState,
                hazeStyle,
                modifier
                    .zIndex(zIndex)
                    .hazeSource(hazeState, zIndex)
            )
        }
        Box(
            modifier = Modifier
//                .background(Color.Yellow)
                .size(300.dp)
                .hazeSource(hazeState, zIndex = 0f)
                .graphicsLayer {
                    rotationZ = 360 * animationProgress
                }
                .paint(
                    painter = painterResource(id = R.drawable.bg),
                )

        )
    }
}

@Composable
fun RoundedBox(
    size: Int,
    zIndex: Float,
    rotationAngle: Float,
    hazeState: HazeState,
    hazeStyle: HazeStyle,
    modifier: Modifier
) {
    val listColors = listOf(Color.Yellow, Color.Red, Color.Blue, Color.Green)
    val tileSize = with(LocalDensity.current) {
        50.dp.toPx()
    }
    val boxWidth = 200.dp
    val boxHeight = 200.dp

    Box(modifier = modifier.size(width = boxWidth, height = boxHeight)) {
        Box(
            modifier = Modifier
                .size(width = boxWidth / 2, height = boxHeight)
                .offset(x = boxWidth / 2)
                .graphicsLayer {
                    rotationY = rotationAngle
                    rotationX = -45f

                    cameraDistance = 100f
                    transformOrigin = TransformOrigin(
                        pivotFractionX = 0.0f,
                        pivotFractionY = 0.0f,
                    )
                }
                .hazeEffect(hazeState, style = hazeStyle)
                .border(
                    8.dp,
                    Color(0xFF24F4FE)
                )
//                .background(
//                    Brush.horizontalGradient(
//                        listColors,
//                        endX = tileSize,
//                        tileMode = TileMode.Repeated
//                    )
//                )

        ) {
            Text("$zIndex", color = Color.White)
        }
    }
}


@Preview(showBackground = true, backgroundColor = 0xFF000023)
@Composable
fun PerplexityLogoPreview() {
    Box(modifier = Modifier.fillMaxSize()) {
        PerplexityLogo(0.0f)
        //RotatingBoxes()
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
fun PreviewRoundedBox() {

    val containerColor = MaterialTheme.colorScheme.surface
    val lightAlpha = 0.3f
    val darkAlpha = 0.1f
    val hazeStyle = HazeStyle(
        backgroundColor = containerColor,
        tints = listOf(
            HazeTint(
                containerColor.copy(alpha = if (containerColor.luminance() >= 0.5) lightAlpha else darkAlpha),
            )
        ),
        blurRadius = 24.dp,
        noiseFactor = 0.1f,
        fallbackTint = HazeTint.Unspecified,
    )

    val hazeState = remember { HazeState() }
    RoundedBox(8, 0f, 0.5f, hazeState, hazeStyle, modifier = Modifier)
}
