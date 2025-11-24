package dev.cankolay.app.android.domain.repository.application

import dev.cankolay.app.android.domain.model.application.SettingsState
import kotlinx.coroutines.flow.Flow

interface SettingsStateRepository {
    val state: Flow<SettingsState>
    suspend fun update(state: SettingsState)
}