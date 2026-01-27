package dev.cankolay.app.android.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.cankolay.app.android.domain.model.api.ApiResult
import dev.cankolay.app.android.domain.model.api.post.Post
import dev.cankolay.app.android.domain.usecase.api.post.GetAllPostsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class PostEvent {
    object FetchPosts : PostEvent()
}

@HiltViewModel
class PostViewModel @Inject constructor(
    private val getAllPostsUseCase: GetAllPostsUseCase,
) : ViewModel() {
    private val _posts = MutableStateFlow<ApiResult<List<Post>>>(value = ApiResult.Loading)
    val posts = _posts.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
        initialValue = ApiResult.Loading
    )

    fun onEvent(event: PostEvent) {
        viewModelScope.launch {
            when (event) {
                is PostEvent.FetchPosts -> {
                    _posts.value = getAllPostsUseCase()
                }
            }
        }
    }
}
