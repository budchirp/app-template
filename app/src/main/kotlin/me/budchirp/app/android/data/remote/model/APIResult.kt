package me.budchirp.app.android.data.remote.model

import me.budchirp.app.android.data.remote.error.APIErrorReason

sealed class APIResult<out T : Any> {
    data class Success<out T : Any>(
        val data: T,
    ) : APIResult<T>()

    data class Error(
        val message: String,
        val reason: APIErrorReason,
    ) : APIResult<Nothing>()

    data class Fatal(
        val throwable: Throwable,
    ) : APIResult<Nothing>()
}