import androidx.compose.animation.core.*
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import dev.chrisbanes.haze.HazeState
import kotlinx.coroutines.launch
import kotlin.math.abs

@Composable
fun RotatingBoxes(
    size: Int = 8,
    initialRotationDuration: Int = 2000,
    // Add decay configuration parameters
    decayStiffness: Float = Spring.StiffnessVeryLow / 4, // Much lower stiffness for slower decay
    decayDampingRatio: Float = Spring.DampingRatioLowBouncy * 0.7f, // Lower damping for more smoothness
    minimumVelocityThreshold: Float = 0.1f, // Minimum velocity to continue animation
    modifier: Modifier = Modifier
) {

    val hazeState = remember { HazeState() }
    var rotationState by remember { mutableStateOf(0f) }
    var isInitialAnimationComplete by remember { mutableStateOf(false) }
    var velocityTracker by remember { mutableStateOf(0f) }

    val scope = rememberCoroutineScope()
    val initialRotationAnim = remember { Animatable(0f) }

    // Custom decay animation spec with slower decay
    val decayAnimationSpec = remember(decayStiffness, decayDampingRatio) {
        spring<Float>(
            dampingRatio = decayDampingRatio,
            stiffness = decayStiffness,
            visibilityThreshold = minimumVelocityThreshold
        )
    }

    LaunchedEffect(Unit) {
        initialRotationAnim.animateTo(
            targetValue = -360f,
            animationSpec = tween(
                durationMillis = initialRotationDuration,
                easing = LinearEasing
            )
        )
        isInitialAnimationComplete = true
    }

    val gestureModifier = Modifier.pointerInput(Unit) {
        detectHorizontalDragGestures(
            onDragStart = { _ ->
                velocityTracker = 0f
            },
            onDragEnd = {
                scope.launch {
                    val initialVelocity = velocityTracker
                    val animatable = Animatable(initialVelocity)

                    // Custom decay animation that continues until velocity is very low
                    animatable.animateTo(
                        targetValue = 0f,
                        animationSpec = decayAnimationSpec,
                        initialVelocity = initialVelocity
                    ) {
                        rotationState += value
                        velocityTracker = value

                        // Optional: Stop animation when velocity is very low
                        if (abs(velocity) < minimumVelocityThreshold) {
                            velocityTracker = 0f
                        }
                    }
                }
            }
        ) { change, dragAmount ->
            val sensitivity = 0.2f
            val newVelocity = dragAmount * sensitivity
            velocityTracker = newVelocity
            rotationState += newVelocity
        }
    }

    Box(modifier = modifier.then(gestureModifier)) {
        MutableList(size) { index ->
            val baseAngle = (index * 360f / size)
            val finalRotation = if (isInitialAnimationComplete) {
                (baseAngle + rotationState) % 360f
            } else {
                (baseAngle + initialRotationAnim.value) % 360f
            }

            val zIndex = (if (finalRotation <= 90) 90 - finalRotation else finalRotation - 90).let {
                if (it > 180) 360 - it else it
            }

//            RoundedBox(
//                size = size,
//                zIndex = zIndex,
//                rotationAngle = finalRotation,
//                hazeState = hazeState,
//                modifier = Modifier.zIndex(zIndex)
//            )
        }
    }
}