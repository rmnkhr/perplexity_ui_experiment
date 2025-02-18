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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi
import dev.chrisbanes.haze.materials.HazeMaterials

@OptIn(ExperimentalHazeMaterialsApi::class)
@Composable
fun PerplexityLogo(
    modifier: Modifier = Modifier
) {

    //Infinite animation from 1 to 0

    val hazeState = remember { HazeState() }
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
        contentAlignment = Alignment.Center,
    ) {
        val size = 8
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
                modifier
                    .zIndex(zIndex)
                .hazeSource(hazeState, zIndex+1f)
            )
        }
        Box(
            modifier = Modifier
//                .background(Color.Yellow)
//                //.hazeEffect(hazeState,  style = HazeMaterials.thin())
                .size(300.dp)
                .hazeSource(hazeState, zIndex = 0f)
//                .paint(
//                    painter = painterResource(id = R.drawable.user_avatar),
//                )
        )
        Box(contentAlignment = Alignment.Center) {

            // Rear card
//            CreditCard(
//                modifier = Modifier
//                    .size(200.dp)
//                    .hazeSource(hazeState, zIndex = 1f)
//                    .graphicsLayer {
//                        rotationY = 90f * animationProgress
//                    }
//                    .zIndex(1f)
//                    .border(24.dp, Color.Green)
//                    .hazeEffect(hazeState, style = HazeMaterials.thin())
//
////                    .shadow(
////                        elevation = 8.dp,)
//            )
//
////            // Middle card
//            CreditCard(
//                modifier = Modifier
//                    .size(300.dp)
//                    .zIndex(2f)
//                    .graphicsLayer {
//                        rotationY = -90f * animationProgress
//                    }
//                    .hazeSource(hazeState, zIndex = 2f)
//                    .hazeEffect(
//                        hazeState, style = HazeMaterials.thin()
//                    )
//
////                    .shadow(
////                        elevation = 8.dp)
//            )
//
//            // Front card
//            CreditCard(
//                modifier = Modifier
//                    .size(100.dp)
//                    .hazeSource(hazeState, zIndex = 3f)
//                    .hazeEffect(hazeState,style = HazeMaterials.thin())
//            )
        }
    }
}

@Composable
fun RoundedBox(
    size: Int,
    zIndex: Float,
    rotationAngle: Float,
    hazeState: HazeState,
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
                .border(
                    16.dp,
                    Color(0xFF24F4FE)
                )
                //.padding(16.dp)
                //.hazeEffect(hazeState, style = HazeMaterials.thin())
//                .background(
//                    Brush.horizontalGradient(
//                        listColors,
//                        endX = tileSize,
//                        tileMode = TileMode.Repeated
//                    )
//                )

        ) {
            //Text("$zIndex", color = Color.White)
        }
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
    val hazeState = remember { HazeState() }
    RoundedBox(8, 0f, 0.5f, hazeState, modifier = Modifier)
}
