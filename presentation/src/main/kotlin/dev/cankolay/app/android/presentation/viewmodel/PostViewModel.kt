package dev.cankolay.app.android.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.cankolay.app.android.domain.model.api.ApiResult
import dev.cankolay.app.android.domain.usecase.post.GetAllPostsUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    getAllPostsUseCase: GetAllPostsUseCase,
) : ViewModel() {
    val posts = flow {
        emit(value = getAllPostsUseCase())
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
        initialValue = ApiResult.Loading
    )
}
