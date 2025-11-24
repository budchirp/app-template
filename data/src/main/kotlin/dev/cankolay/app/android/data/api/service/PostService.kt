package dev.cankolay.app.android.data.api.service

import dev.cankolay.app.android.data.api.ApiConstants
import dev.cankolay.app.android.data.api.client.KtorClient
import dev.cankolay.app.android.data.api.client.request
import dev.cankolay.app.android.data.api.model.response.post.PostDto
import dev.cankolay.app.android.domain.model.api.ApiResult
import io.ktor.client.request.get
import io.ktor.http.path
import javax.inject.Inject

class PostService
@Inject
constructor(val client: KtorClient) {
    suspend fun getAll(): ApiResult<List<PostDto>> = request(response = true) {
        client().get {
            url {
                path(ApiConstants.Endpoints.POSTS)
            }
        }
    }
}