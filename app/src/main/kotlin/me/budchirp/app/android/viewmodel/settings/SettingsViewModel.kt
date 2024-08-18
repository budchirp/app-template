package me.budchirp.app.android.viewmodel.settings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import me.budchirp.app.android.data.datastore.DefaultSettings
import me.budchirp.app.android.data.datastore.model.NullableSettings
import me.budchirp.app.android.data.datastore.model.Settings
import me.budchirp.app.android.data.repository.SettingsRepository
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel
    @Inject
    constructor(
        private val settingsRepository: SettingsRepository,
    ) : ViewModel() {
        private val _isReady: MutableStateFlow<Boolean> = MutableStateFlow(value = false)
        val isReady: StateFlow<Boolean> = _isReady.asStateFlow()

        private val settingsFlow: Flow<Settings> = settingsRepository.settingsFlow

        @Composable
        fun getSettings(): State<Settings> =
            settingsFlow.collectAsStateWithLifecycle(
                initialValue =
                    DefaultSettings(),
            )

        fun updateSettings(settings: NullableSettings) {
            viewModelScope.launch {
                settingsRepository.saveSettings(
                    settings = settings,
                )
            }
        }

        init {
            viewModelScope.launch {
                settingsFlow.collect {
                    _isReady.value = true
                }
            }
        }
    }