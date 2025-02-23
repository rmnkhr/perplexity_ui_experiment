package com.example.perplexity

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.layout.ContentScale
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
    hazeStateLeft: HazeState,
    hazeStateRight: HazeState,
    sliderValue: Float,
    modifier: Modifier
) {
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
        blurRadius = 5.dp,
        noiseFactor = 0.3f,
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
    Box(modifier = modifier) {
        Box(modifier = Modifier.fillMaxSize()) {
            val pages = 8
            MutableList(pages) { index ->
                val rotationAngle = (animationProgress * 360 + (index * 360 / pages)) % 360
                if (rotationAngle > 0f && rotationAngle < 180f) {
                    RoundedBoxLeft(
                        rotationAngle - 90f,
                        hazeStateLeft,
                        hazeStyle,
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth(0.5f)
                            .align(Alignment.CenterStart)
                            .zIndex(rotationAngle)
                            .hazeSource(hazeStateLeft, rotationAngle + 1f)
                    )
                } else {
                    RoundedBoxRight(
                        rotationAngle + 90f,
                        hazeStateRight,
                        hazeStyle,
                        modifier = Modifier
                            //.background(Color.Yellow)
                            .fillMaxHeight()
                            .fillMaxWidth(0.5f)
                            .align(Alignment.CenterEnd)
                            .zIndex(180 - rotationAngle)
                            .hazeSource(hazeStateRight, 360 - rotationAngle + 1f)
                    )
                }
            }

        }
    }
}

@Composable
fun RoundedBoxRight(
    rotationAngle: Float,
    hazeState: HazeState,
    hazeStyle: HazeStyle,
    modifier: Modifier
) {
    Page(
        rotationAngle,
        hazeState = hazeState,
        hazeStyle = hazeStyle,
        borderColor = Color(0xFF24F4FE),
        modifier = modifier
            //.size(size)
            .graphicsLayer {
                rotationY = rotationAngle
                rotationX = -45f

                cameraDistance = 100f
                transformOrigin = TransformOrigin(
                    pivotFractionX = 0f,
                    pivotFractionY = 0.0f,
                )
            }
    )
}

@Composable
fun RoundedBoxLeft(
    rotationAngle: Float,
    hazeState: HazeState,
    hazeStyle: HazeStyle,
    modifier: Modifier
) {

    Page(
        rotationAngle,
        hazeState = hazeState,
        hazeStyle = hazeStyle,
        borderColor = Color(0xFF24F4FE),
        modifier = modifier
            //.size(size)
            .graphicsLayer {
                rotationY = rotationAngle
                rotationX = -45f

                cameraDistance = 100f
                transformOrigin = TransformOrigin(
                    pivotFractionX = 1f,
                    pivotFractionY = 0.0f,
                )
            }
    )
}

@Composable
fun Page(
    rotationAngle: Float,
    hazeState: HazeState,
    hazeStyle: HazeStyle,
    borderColor: Color,
    modifier: Modifier
) {
    Box(
        modifier = modifier
            .hazeEffect(hazeState, style = hazeStyle)
            .border(8.dp, borderColor)
    ) {
    }

}


@Preview(showBackground = true, backgroundColor = 0xFF000023)
@Composable
fun PerplexityLogoPreview() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        val hazeStateLeft = remember { HazeState() }
        val hazeStateRight = remember { HazeState() }

        //background
        Box(
            modifier = Modifier
                .hazeSource(hazeStateRight, zIndex = 0f)
                .hazeSource(hazeStateLeft, zIndex = 0f)
                .clip(CircleShape)
                .paint(
                    painter = painterResource(id = R.drawable.bg),
                    contentScale = ContentScale.Crop
                )

        )
        PerplexityLogo(
            hazeStateLeft, hazeStateRight,
            0.0f, modifier = Modifier.size(200.dp))
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF000023)
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
    RoundedBoxRight(
        0.5f, hazeState, hazeStyle, modifier = Modifier
    )
}
