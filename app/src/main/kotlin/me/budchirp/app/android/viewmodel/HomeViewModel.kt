package me.budchirp.app.android.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
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
        val postsFlow: MutableStateFlow<APIResult<List<PostModel>>?> =
            MutableStateFlow(value = null)

        fun fetchPosts() {
            viewModelScope.launch {
                postsFlow.value = postService.getPosts()
            }
        }

        init {
            fetchPosts()
        }
    }