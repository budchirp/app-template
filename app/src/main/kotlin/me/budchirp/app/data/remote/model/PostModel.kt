package me.budchirp.app.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class PostModel(
    val id: Int,
    val title: String,
    val body: String
)