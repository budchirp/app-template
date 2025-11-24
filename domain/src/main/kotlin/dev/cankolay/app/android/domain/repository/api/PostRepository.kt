package dev.cankolay.app.android.domain.repository.api

import dev.cankolay.app.android.domain.model.api.ApiResult
import dev.cankolay.app.android.domain.model.api.post.Post

interface PostRepository {
    suspend fun getAll(): ApiResult<List<Post>>
}