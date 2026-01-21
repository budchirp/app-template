package dev.cankolay.app.android.data.api.model.response

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    val error: Boolean,
    val message: String
)

@Serializable
data class SuccessResponse<T>(
    val error: Boolean,
    val message: String,
    val data: T
)
