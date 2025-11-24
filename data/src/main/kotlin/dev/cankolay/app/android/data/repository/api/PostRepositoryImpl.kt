package dev.cankolay.app.android.data.repository.api

import dev.cankolay.app.android.data.api.model.response.post.toDomain
import dev.cankolay.app.android.data.api.service.PostService
import dev.cankolay.app.android.domain.model.api.ApiResult
import dev.cankolay.app.android.domain.model.api.post.Post
import dev.cankolay.app.android.domain.repository.api.PostRepository
import javax.inject.Inject

class PostRepositoryImpl
@Inject
constructor(val postService: PostService) : PostRepository {
    override suspend fun getAll(): ApiResult<List<Post>> =
        when (val result = postService.getAll()) {
            is ApiResult.Success -> ApiResult.Success(
                message = result.message,
                data = result.data.map { it.toDomain() })

            is ApiResult.Loading -> result
            
            is ApiResult.Error -> result
            is ApiResult.Fatal -> result
        }
}