package me.budchirp.app.android.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.budchirp.app.android.data.datastore.SettingsDataStore
import javax.inject.Inject

@HiltViewModel
class AppViewModel
    @Inject
    constructor(settingsDataStore: SettingsDataStore) : ViewModel() {
        var isLoading: Boolean by mutableStateOf(value = true)
            private set

        var isDrawerEnabled: Boolean by mutableStateOf(value = true)
            private set

        fun setIsDrawerEnabled(value: Boolean) {
            isDrawerEnabled = value
        }

    init {
        viewModelScope.launch {
            settingsDataStore.flow.collect {
                isLoading = false
            }
        }
    } }