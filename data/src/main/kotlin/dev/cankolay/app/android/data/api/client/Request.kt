package dev.cankolay.app.android.data.api.client

import dev.cankolay.app.android.data.api.model.response.ErrorResponse
import dev.cankolay.app.android.data.api.model.response.SuccessResponse
import dev.cankolay.app.android.domain.model.api.ApiResult
import dev.cankolay.app.android.domain.model.api.ErrorReason
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.statement.HttpResponse
import kotlinx.serialization.Serializable

suspend inline fun <reified T : @Serializable Any> request(
    block: suspend () -> HttpResponse
): ApiResult<T> {
    return try {
        val response = block()

        val statusCode = response.status.value
        if (statusCode in 200..299) {
            val body = response.body<SuccessResponse<T>>()
            if (body.error) ApiResult.Error(
                message = body.message,
                reason = ErrorReason.CLIENT
            ) else ApiResult.Success(
                message = body.message,
                data = body.data
            )
        } else {
            val body = response.body<ErrorResponse>()

            when (statusCode) {
                in 300..399 -> {
                    ApiResult.Error(
                        message = body.message,
                        reason = ErrorReason.SERVER
                    )
                }

                in 400..499 -> {
                    ApiResult.Error(message = body.message, reason = ErrorReason.CLIENT)
                }

                in 500..599 -> {
                    ApiResult.Error(
                        message = body.message,
                        reason = ErrorReason.SERVER
                    )
                }

                else -> {
                    ApiResult.Error(
                        message = body.message,
                        reason = ErrorReason.SERVER
                    )
                }
            }
        }
    } catch (e: RedirectResponseException) {
        return ApiResult.Error(message = e.message, reason = ErrorReason.SERVER)
    } catch (e: ClientRequestException) {
        return ApiResult.Error(message = e.message, reason = ErrorReason.CLIENT)
    } catch (e: ServerResponseException) {
        return ApiResult.Error(message = e.message, reason = ErrorReason.SERVER)
    } catch (e: Exception) {
        return ApiResult.Fatal(exception = e)
    }
}

suspend fun request(
    no_return: Boolean,
    block: suspend () -> HttpResponse
): ApiResult<Nothing?> {
    return try {
        val response = block()

        val statusCode = response.status.value
        if (statusCode in 200..299) {
            val body = response.body<SuccessResponse<Nothing?>>()
            if (body.error) ApiResult.Error(
                message = body.message,
                reason = ErrorReason.CLIENT
            ) else ApiResult.Success(
                message = body.message,
                data = null
            )
        } else {
            val body = response.body<ErrorResponse>()

            when (statusCode) {
                in 300..399 -> {
                    ApiResult.Error(
                        message = body.message,
                        reason = ErrorReason.SERVER
                    )
                }

                in 400..499 -> {
                    ApiResult.Error(message = body.message, reason = ErrorReason.CLIENT)
                }

                in 500..599 -> {
                    ApiResult.Error(
                        message = body.message,
                        reason = ErrorReason.SERVER
                    )
                }

                else -> {
                    ApiResult.Error(
                        message = body.message,
                        reason = ErrorReason.SERVER
                    )
                }
            }
        }
    } catch (e: RedirectResponseException) {
        return ApiResult.Error(message = e.message, reason = ErrorReason.SERVER)
    } catch (e: ClientRequestException) {
        return ApiResult.Error(message = e.message, reason = ErrorReason.CLIENT)
    } catch (e: ServerResponseException) {
        return ApiResult.Error(message = e.message, reason = ErrorReason.SERVER)
    } catch (e: Exception) {
        return ApiResult.Fatal(exception = e)
    }
}