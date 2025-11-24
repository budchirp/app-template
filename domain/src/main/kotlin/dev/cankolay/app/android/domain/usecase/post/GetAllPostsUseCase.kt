package dev.cankolay.app.android.domain.usecase.post

import dev.cankolay.app.android.domain.repository.api.PostRepository
import javax.inject.Inject

class GetAllPostsUseCase
@Inject
constructor(private val postRepository: PostRepository) {
    suspend operator fun invoke() = postRepository.getAll()
}