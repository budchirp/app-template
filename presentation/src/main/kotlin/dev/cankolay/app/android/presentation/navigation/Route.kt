package dev.cankolay.app.android.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Route {
    @Serializable
    data object Home :
        Route()

    @Serializable
    data object Settings :
        Route()

    @Serializable
    data object Appearance :
        Route()

    @Serializable
    data object Theme :
        Route()

    @Serializable
    data object MaterialYou :
        Route()

    @Serializable
    data object Languages :
        Route()

    @Serializable
    data object About :
        Route()
}


