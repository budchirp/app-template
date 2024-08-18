package me.budchirp.app.android.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import me.budchirp.app.android.data.datastore.model.NullableSettings
import me.budchirp.app.android.data.datastore.model.Settings
import me.budchirp.app.android.presentation.theme.Theme
import java.io.IOException
import javax.inject.Inject

private val Context.settingsDataStore by preferencesDataStore(name = "settings")

fun DefaultSettings(): Settings =
    Settings(
        exampleField = "example",
        theme = Theme.SYSTEM,
        materialYou = false,
    )

class SettingsDataStore
    @Inject
    constructor(
        @ApplicationContext private val context: Context,
    ) {
        private val dataStore: DataStore<Preferences> = context.settingsDataStore

        private object PreferenceKeys {
            val EXAMPLE_FIELD: Preferences.Key<String> =
                stringPreferencesKey(name = "example_field")

            val THEME: Preferences.Key<String> = stringPreferencesKey(name = "theme")
            val MATERIAL_YOU: Preferences.Key<Boolean> =
                booleanPreferencesKey(name = "material_you")
        }

        suspend fun saveSettings(settings: NullableSettings) {
            val existingSettings: Settings = settingsFlow.first()

            dataStore.edit { preferences: MutablePreferences ->
                preferences[PreferenceKeys.EXAMPLE_FIELD] =
                    settings.exampleField ?: existingSettings.exampleField

                preferences[PreferenceKeys.THEME] =
                    settings.theme?.name ?: existingSettings.theme.name
                preferences[PreferenceKeys.MATERIAL_YOU] =
                    settings.materialYou ?: existingSettings.materialYou
            }
        }

        val settingsFlow: Flow<Settings> =
            dataStore
                .data
                .catch { exception: Throwable ->
                    if (exception is IOException) {
                        DefaultSettings()
                    } else {
                        throw exception
                    }
                }.map { preferences: Preferences ->
                    val exampleField: String =
                        preferences[PreferenceKeys.EXAMPLE_FIELD] ?: DefaultSettings().exampleField

                    val theme: Theme =
                        preferences[PreferenceKeys.THEME]?.let { type: String ->
                            Theme.valueOf(value = type)
                        }
                            ?: DefaultSettings().theme
                    val materialYou =
                        preferences[PreferenceKeys.MATERIAL_YOU] ?: DefaultSettings().materialYou

                    Settings(
                        exampleField = exampleField,
                        theme = theme,
                        materialYou = materialYou,
                    )
                }
    }