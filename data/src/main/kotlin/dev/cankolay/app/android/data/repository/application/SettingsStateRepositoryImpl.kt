package dev.cankolay.app.android.data.repository.application

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import dev.cankolay.app.android.data.di.SettingsDataStore
import dev.cankolay.app.android.domain.model.application.MaterialYou
import dev.cankolay.app.android.domain.model.application.SettingsState
import dev.cankolay.app.android.domain.model.application.Theme
import dev.cankolay.app.android.domain.repository.application.SettingsStateRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class SettingsStateRepositoryImpl
@Inject
constructor(
    @SettingsDataStore
    private val dataStore: DataStore<Preferences>
) : SettingsStateRepository {
    private object PreferenceKeys {
        val THEME = stringPreferencesKey(name = "theme")

        val IS_AMOLED = booleanPreferencesKey(name = "is_amoled")

        val MATERIAL_YOU = stringPreferencesKey(name = "material_you")
    }

    override suspend fun update(state: SettingsState) {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.THEME] = state.theme.name

            preferences[PreferenceKeys.IS_AMOLED] = state.isAmoled

            preferences[PreferenceKeys.MATERIAL_YOU] = when (state.materialYou) {
                is MaterialYou.OFF -> "off"
                is MaterialYou.SEED -> state.materialYou.color.toArgb().toString()
                is MaterialYou.WALLPAPER -> "wallpaper"
            }
        }
    }

    private val default = SettingsState()

    override val state = dataStore.data.catch { exception ->
        if (exception is IOException) {
            default
        } else {
            throw exception
        }
    }.map { preferences ->
        SettingsState(
            theme = Theme.valueOf(value = preferences[PreferenceKeys.THEME] ?: default.theme.name),

            isAmoled = preferences[PreferenceKeys.IS_AMOLED] ?: default.isAmoled,

            materialYou = when (preferences[PreferenceKeys.MATERIAL_YOU]) {
                "off" -> MaterialYou.OFF
                "wallpaper" -> MaterialYou.WALLPAPER
                null -> default.materialYou
                else -> MaterialYou.SEED(color = Color(color = preferences[PreferenceKeys.MATERIAL_YOU]!!.toInt()))
            }
        )
    }
}