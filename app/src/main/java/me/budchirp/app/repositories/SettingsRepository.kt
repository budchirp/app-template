package me.budchirp.app.repositories

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import me.budchirp.app.ui.theme.Theme

data class SettingsData(
    val theme: Theme = Theme.SYSTEM,
    val materialYou: Boolean = true,
)

private val Context.dataStore by preferencesDataStore(name = "settings")

class SettingsRepository @Inject constructor(@ApplicationContext context: Context) {
  private val dataStore = context.dataStore

  private object PreferenceKeys {
    val THEME = stringPreferencesKey("theme")
    val MATERIAL_YOU = booleanPreferencesKey("material_you")
  }

  suspend fun saveSettings(settingsData: SettingsData) {
    dataStore.edit { preferences ->
      preferences[PreferenceKeys.THEME] = settingsData.theme.name
      preferences[PreferenceKeys.MATERIAL_YOU] = settingsData.materialYou
    }
  }

  val settings: Flow<SettingsData> =
      dataStore
          .data
          .map { preferences ->
            val theme =
                preferences[PreferenceKeys.THEME]?.let { type -> Theme.valueOf(type) }
                    ?: SettingsData().theme
            val materialYou = preferences[PreferenceKeys.MATERIAL_YOU] ?: SettingsData().materialYou

            SettingsData(theme = theme, materialYou = materialYou)
          }
          .distinctUntilChanged()
}
