package dev.cankolay.app.android.data.api.model.response.post

import dev.cankolay.app.android.domain.model.api.post.Post
import kotlinx.serialization.Serializable

@Serializable
data class PostDto(
    val id: Int,
    val title: String,
    val description: String
)

fun PostDto.toDomain(): Post {
    return Post(
        id = id,
        title = title,
        description = description
    )
}