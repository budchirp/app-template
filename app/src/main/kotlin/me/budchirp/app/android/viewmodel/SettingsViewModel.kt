package me.budchirp.app.android.viewmodel

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
import me.budchirp.app.android.data.datastore.SettingsDataStore
import me.budchirp.app.android.data.datastore.defaultSettings
import me.budchirp.app.android.data.datastore.model.NullableSettings
import me.budchirp.app.android.data.datastore.model.Settings
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel
    @Inject
    constructor(
        private val settingsDataStore: SettingsDataStore,
    ) : ViewModel() {
        private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(value = true)
        val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

        private val settingsFlow: Flow<Settings> = settingsDataStore.flow

        @Composable
        fun getSettings(): State<Settings> =
            settingsFlow.collectAsStateWithLifecycle(
                initialValue =
                    defaultSettings(),
            )

        fun updateSettings(settings: NullableSettings) {
            viewModelScope.launch {
                settingsDataStore.update(
                    settings = settings,
                )
            }
        }

        init {
            viewModelScope.launch {
                settingsFlow.collect {
                    _isLoading.value = true
                }
            }
        }
    }