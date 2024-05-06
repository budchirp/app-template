package me.budchirp.app.ui.views

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import me.budchirp.app.data.remote.model.APIResult
import me.budchirp.app.data.remote.model.PostModel
import me.budchirp.app.data.remote.service.PostService
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
@Inject
constructor(private val postService: PostService) :
    ViewModel() {
    val postsFlow: MutableStateFlow<APIResult<List<PostModel>>?> = MutableStateFlow(value = null)

    fun fetchPosts() {
        postsFlow.value = null

        viewModelScope.launch {
            postsFlow.value = postService.getPosts()
        }
    }

    init {
        fetchPosts()
    }
}
