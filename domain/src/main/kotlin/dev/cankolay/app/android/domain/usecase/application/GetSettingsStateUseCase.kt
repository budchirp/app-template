package dev.cankolay.app.android.domain.usecase.application

import dev.cankolay.app.android.domain.repository.application.SettingsStateRepository
import javax.inject.Inject

class GetSettingsStateUseCase @Inject constructor(
    private val settingsStateRepository: SettingsStateRepository
) {
    operator fun invoke() = settingsStateRepository.state
}
