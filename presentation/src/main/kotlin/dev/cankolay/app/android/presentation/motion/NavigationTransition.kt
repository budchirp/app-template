package dev.cankolay.app.android.presentation.motion

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.navigation3.ui.NavDisplay

enum class TransitionType {
    SLIDE,
    FADE,
}

fun navigationTransition(type: TransitionType = TransitionType.SLIDE): Map<String, Any> {
    val fadeIn = fadeIn(
        animationSpec = tween(
            durationMillis = MotionConstants.ENTER_DURATION,
            easing = FastOutSlowInEasing
        )
    )

    val fadeOut = fadeOut(
        animationSpec = tween(
            durationMillis = MotionConstants.EXIT_DURATION,
            easing = FastOutSlowInEasing
        )
    )

    return when (type) {
        TransitionType.SLIDE -> NavDisplay.transitionSpec {
            slideIn(initialOffsetX = { (it * initialOffset).toInt() }) togetherWith slideOut(
                targetOffsetX = { -(it * initialOffset).toInt() })
        } + NavDisplay.popTransitionSpec {
            slideIn(initialOffsetX = { -(it * initialOffset).toInt() }) togetherWith slideOut(
                targetOffsetX = { (it * initialOffset).toInt() })
        }

        TransitionType.FADE -> NavDisplay.transitionSpec {
            fadeIn togetherWith fadeOut
        } + NavDisplay.popTransitionSpec {
            fadeIn togetherWith fadeOut
        }
    }
}