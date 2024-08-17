package me.budchirp.app.android.data.repository

import kotlinx.coroutines.flow.Flow
import me.budchirp.app.android.data.datastore.SettingsDataStore
import me.budchirp.app.android.data.datastore.model.NullableSettings
import me.budchirp.app.android.data.datastore.model.Settings
import javax.inject.Inject

class SettingsRepository
    @Inject
    constructor(
        private val settingsDataStore: SettingsDataStore,
    ) {
        suspend fun saveSettings(settings: NullableSettings) {
            settingsDataStore.saveSettings(settings)
        }

        val settingsFlow: Flow<Settings> = settingsDataStore.settingsFlow
    }