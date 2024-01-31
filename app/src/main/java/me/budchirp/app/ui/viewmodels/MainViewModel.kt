package me.budchirp.app.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch
import me.budchirp.app.repositories.SettingsData
import me.budchirp.app.repositories.SettingsRepository
import me.budchirp.app.ui.theme.Theme

@HiltViewModel
class MainViewModel @Inject constructor(private val settingsRepository: SettingsRepository) :
    ViewModel() {
  val settingsData = settingsRepository.settings

  fun updateTheme(theme: Theme, settingsData: SettingsData) {
    viewModelScope.launch { settingsRepository.saveSettings(settingsData.copy(theme = theme)) }
  }

  fun updateMaterialYou(materialYou: Boolean, settingsData: SettingsData) {
    viewModelScope.launch {
      settingsRepository.saveSettings(settingsData.copy(materialYou = materialYou))
    }
  }
}
