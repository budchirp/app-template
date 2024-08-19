package me.budchirp.app.android.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AppViewModel
    @Inject
    constructor() : ViewModel() {
        private val _enableDrawer: MutableStateFlow<Boolean> = MutableStateFlow(value = true)
        val enableDrawer: StateFlow<Boolean> = _enableDrawer.asStateFlow()

        fun setEnableDrawer(enable: Boolean) {
            _enableDrawer.value = enable
        }
    }