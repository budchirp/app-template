package dev.cankolay.app.android.presentation.composable

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dev.cankolay.app.android.presentation.motion.MotionConstants
import dev.cankolay.app.android.presentation.motion.initialOffset
import dev.cankolay.app.android.presentation.motion.slideIn
import dev.cankolay.app.android.presentation.motion.slideOut
import dev.cankolay.app.android.presentation.navigation.Route
import kotlinx.serialization.Serializable

enum class AnimationType {
    SLIDE,
    FADE,
    NONE
}

@OptIn(ExperimentalMaterial3Api::class)
inline fun <reified T : @Serializable Route> NavGraphBuilder.animatedComposable(
    animationType: AnimationType = AnimationType.SLIDE,
    crossinline content: @Composable AnimatedContentScope.(backStackEntry: NavBackStackEntry) -> Unit,
) {
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

    composable<T>(
        enterTransition = {
            when (animationType) {
                AnimationType.SLIDE -> slideIn(initialOffsetX = { (it * initialOffset).toInt() })
                AnimationType.FADE -> fadeIn
                AnimationType.NONE -> EnterTransition.None
            }
        },
        exitTransition = {
            when (animationType) {
                AnimationType.SLIDE -> slideOut(targetOffsetX = { -(it * initialOffset).toInt() })
                AnimationType.FADE -> fadeOut
                AnimationType.NONE -> ExitTransition.None
            }
        },
        popEnterTransition = {
            when (animationType) {
                AnimationType.SLIDE -> slideIn(initialOffsetX = { -(it * initialOffset).toInt() })
                AnimationType.FADE -> fadeIn
                AnimationType.NONE -> EnterTransition.None
            }
        },
        popExitTransition = {
            when (animationType) {
                AnimationType.SLIDE -> slideOut(targetOffsetX = { (it * initialOffset).toInt() })
                AnimationType.FADE -> fadeOut
                AnimationType.NONE -> ExitTransition.None
            }
        }
    ) { backStackEntry ->
        content(this, backStackEntry)
    }
}