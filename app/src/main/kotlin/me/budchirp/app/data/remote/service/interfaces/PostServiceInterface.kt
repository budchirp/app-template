package me.budchirp.app.data.remote.service.interfaces

import me.budchirp.app.data.remote.model.APIResult
import me.budchirp.app.data.remote.model.PostModel

interface PostServiceInterface {
    suspend fun getPosts(): APIResult<List<PostModel>>
    suspend fun getPost(id: Int): PostModel
}