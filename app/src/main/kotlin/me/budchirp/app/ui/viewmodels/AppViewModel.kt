package me.budchirp.app.ui.viewmodels

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import me.budchirp.app.data.repositories.SettingsData
import me.budchirp.app.data.repositories.SettingsRepository
import me.budchirp.app.ui.theme.Theme
import javax.inject.Inject

@HiltViewModel
class AppViewModel
@Inject
constructor(private val settingsRepository: SettingsRepository) :
    ViewModel() {
    private val _isReady = MutableStateFlow(false)
    val isReady = _isReady.asStateFlow()

    private val settingsDataFlow: Flow<SettingsData> = settingsRepository.settingsFlow

    @Composable
    fun getSettingsData(): State<SettingsData> {
        return settingsDataFlow.collectAsStateWithLifecycle(
            initialValue =
            remember {
                SettingsData()
            }
        )
    }

    fun updateTheme(theme: Theme, settingsData: SettingsData) {
        viewModelScope.launch {
            settingsRepository.saveSettings(
                settingsData.copy(
                    theme = theme
                )
            )
        }
    }

    fun updateMaterialYou(materialYou: Boolean, settingsData: SettingsData) {
        viewModelScope.launch {
            settingsRepository.saveSettings(settingsData.copy(materialYou = materialYou))
        }
    }

    init {
        viewModelScope.launch {
            settingsDataFlow.collect {
                _isReady.value = true
            }
        }
    }
}