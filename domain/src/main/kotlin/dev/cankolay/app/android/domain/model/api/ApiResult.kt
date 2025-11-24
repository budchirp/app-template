package dev.cankolay.app.android.domain.model.api

enum class ErrorReason {
    CLIENT,
    SERVER,
}

sealed class ApiResult<out T> {
    data class Success<out T>(
        val message: String,
        val data: T
    ) :
        ApiResult<T>()

    data class Error(val message: String, val reason: ErrorReason) : ApiResult<Nothing>()

    data class Fatal(val exception: Throwable) : ApiResult<Nothing>()

    data object Loading : ApiResult<Nothing>()

}