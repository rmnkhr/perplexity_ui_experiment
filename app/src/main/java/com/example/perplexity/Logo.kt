package com.example.perplexity

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun PerplexityLogo(
    modifier: Modifier = Modifier
) {

    //Infinite animation from 1 to 0
    val infiniteTransition = rememberInfiniteTransition()
    val animationProgress by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(5_000),
            repeatMode = RepeatMode.Restart
        )
    )

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        val size = 8
        MutableList(size) { index ->
            RoundedBox(
                size,
                index,
                animationProgress,
                modifier = Modifier
            )
        }
    }
}

@Composable
fun RoundedBox(
    size: Int,
    index: Int,
    animationProgress: Float,
    modifier: Modifier
) {
    val listColors = listOf(Color.Yellow, Color.Red, Color.Blue)
    val tileSize = with(LocalDensity.current) {
        50.dp.toPx()
    }
    val boxWidth = 200.dp
    val boxHeight = 200.dp

    Box(
        modifier = modifier
            .size(width = boxWidth, height = boxHeight)
    ) {
        Box(
            modifier = Modifier
                .size(width = boxWidth / 2, height = boxHeight)
                .offset(x = boxWidth / 2)
                .graphicsLayer {
                    rotationY = animationProgress * 360 + (index * 360 / size)
                    rotationX = -45f
                    cameraDistance = 100f
                    transformOrigin = TransformOrigin(
                        pivotFractionX = 0.0f,
                        pivotFractionY = 0.1f,
                    )
                }
                .clip(RoundedCornerShape(4.dp)) // Clip for rounded corners
                .border(
                    8.dp,
                    Color(0xFF24F4FE),
                    RoundedCornerShape(4.dp)
                )

//                .background(
//                    Color.Blue
////                Brush.horizontalGradient(
////                    listColors,
////                    endX = tileSize,
////                    tileMode = TileMode.Repeated
////                )
//                )

        ) {}
    }
}


@Preview(showBackground = true, backgroundColor = 0xFF000023)
@Composable
fun PerplexityLogoPreview() {
    Box(modifier = Modifier.fillMaxSize()) {
        PerplexityLogo()
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
fun PreviewRoundedBox() {
    RoundedBox(8, 0, 0.5f, modifier = Modifier)
}
