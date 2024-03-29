package me.budchirp.app.ui.motion

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally

public object MotionConstants {
  public const val DefaultMotionDuration: Int = 300
}

private val Int.ForOutgoing: Int
  get() = (this * 0.35f).toInt()

private val Int.ForIncoming: Int
  get() = this - this.ForOutgoing

public fun slideIn(
    initialOffsetX: (fullWidth: Int) -> Int,
    durationMillis: Int = MotionConstants.DefaultMotionDuration,
): EnterTransition =
    slideInHorizontally(
        animationSpec = tween(durationMillis = durationMillis, easing = FastOutSlowInEasing),
        initialOffsetX = initialOffsetX
    ) +
        fadeIn(
            animationSpec =
                tween(
                    durationMillis = durationMillis.ForIncoming,
                    delayMillis = durationMillis.ForOutgoing,
                    easing = LinearOutSlowInEasing
                )
        )

public fun slideOut(
    targetOffsetX: (fullWidth: Int) -> Int,
    durationMillis: Int = MotionConstants.DefaultMotionDuration,
): ExitTransition =
    slideOutHorizontally(
        animationSpec = tween(durationMillis = durationMillis, easing = FastOutSlowInEasing),
        targetOffsetX = targetOffsetX
    ) +
        fadeOut(
            animationSpec =
                tween(
                    durationMillis = durationMillis.ForOutgoing,
                    delayMillis = 0,
                    easing = FastOutLinearInEasing
                )
        )
