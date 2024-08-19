package me.budchirp.app.android.viewmodel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import me.budchirp.app.android.data.remote.error.APIResult
import me.budchirp.app.android.data.remote.model.PostModel
import me.budchirp.app.android.data.remote.service.PostService
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
    @Inject
    constructor(
        private val postService: PostService,
    ) : ViewModel() {
        private val _posts: MutableStateFlow<APIResult<List<PostModel>>?> =
            MutableStateFlow(value = null)
        val posts: StateFlow<APIResult<List<PostModel>>?> = _posts.asStateFlow()

        @Composable
        fun getPosts(): State<APIResult<List<PostModel>>?> =
            posts.collectAsStateWithLifecycle(initialValue = null)

        fun fetchPosts() {
            viewModelScope.launch {
                _posts.value = postService.getPosts()
            }
        }

        init {
            fetchPosts()
        }
    }