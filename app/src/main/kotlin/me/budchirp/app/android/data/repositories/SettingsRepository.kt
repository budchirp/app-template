package me.budchirp.app.android.data.repositories

import android.content.Context
import android.os.Build
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import me.budchirp.app.android.ui.theme.Theme
import javax.inject.Inject

data class SettingsData(
    val theme: Theme = Theme.SYSTEM,
    val materialYou: Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
)

private val Context.settingsDataStore by preferencesDataStore(name = "settings")

class SettingsRepository
@Inject
constructor(
    @ApplicationContext private val context: Context
) {
    private val dataStore = context.settingsDataStore

    private object PreferenceKeys {
        val THEME = stringPreferencesKey(name = "theme")
        val MATERIAL_YOU = booleanPreferencesKey(name = "material_you")
    }

    suspend fun saveSettings(settingsData: SettingsData) {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.THEME] = settingsData.theme.name
            preferences[PreferenceKeys.MATERIAL_YOU] = settingsData.materialYou
        }
    }

    val settingsFlow: Flow<SettingsData> =
        dataStore
            .data.catch { exception ->
                if (exception is IOException) {
                    SettingsData()
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                val theme =
                    preferences[PreferenceKeys.THEME]?.let { type -> Theme.valueOf(value = type) }
                        ?: SettingsData().theme
                val materialYou =
                    preferences[PreferenceKeys.MATERIAL_YOU] ?: SettingsData().materialYou

                SettingsData(theme = theme, materialYou = materialYou)
            }
}