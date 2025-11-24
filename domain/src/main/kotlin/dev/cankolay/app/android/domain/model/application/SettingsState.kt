package dev.cankolay.app.android.domain.model.application

enum class Theme(
    val type: String,
) {
    SYSTEM("system"),
    DARK("dark"),
    LIGHT("light"),
}


data class SettingsState(
    val theme: Theme = Theme.SYSTEM,
    val materialYou: Boolean = false
)
