package dev.cankolay.app.android.domain.model.application

import androidx.compose.ui.graphics.Color

enum class Theme(
    val type: String,
) {
    SYSTEM(type = "system"),
    DARK(type = "dark"),
    LIGHT(type = "light"),
}

sealed class MaterialYou(
    open val color: Color
) {
    object OFF : MaterialYou(color = Color.Unspecified)

    data class SEED(override val color: Color) : MaterialYou(color = color)

    object WALLPAPER : MaterialYou(color = Color.Unspecified)
}

data class SettingsState(
    val theme: Theme = Theme.SYSTEM,
    val isAmoled: Boolean = false,

    val materialYou: MaterialYou = MaterialYou.OFF
)
