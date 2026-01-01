package dev.cankolay.app.android.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.cankolay.app.android.domain.model.application.SettingsState
import dev.cankolay.app.android.domain.usecase.application.GetSettingsStateUseCase
import dev.cankolay.app.android.domain.usecase.application.UpdateSettingsStateUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    getSettingsStateUseCase: GetSettingsStateUseCase,
    private val updateSettingsStateUseCase: UpdateSettingsStateUseCase
) : ViewModel() {

    val state = getSettingsStateUseCase()
        .stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
            initialValue = null
        )

    fun onEvent(event: SettingsEvent) {
        viewModelScope.launch {
            when (event) {
                is SettingsEvent.UpdateSettings -> {
                    updateSettingsStateUseCase(event.settingsState)
                }
            }
        }
    }
}

sealed class SettingsEvent {
    data class UpdateSettings(val settingsState: SettingsState) : SettingsEvent()
}
