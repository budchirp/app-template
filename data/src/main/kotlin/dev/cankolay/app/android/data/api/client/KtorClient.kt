package dev.cankolay.app.android.data.api.client

import android.content.Context
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.cankolay.app.android.data.api.ApiConstants
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.cache.HttpCache
import io.ktor.client.plugins.cache.storage.FileStorage
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Inject

class KtorClient
@Inject
constructor(
    @ApplicationContext private val context: Context,
) {
    suspend operator fun invoke(): HttpClient {
        val client = HttpClient(engineFactory = OkHttp) {
            defaultRequest {
                url {
                    protocol = URLProtocol.HTTP
                    host = ApiConstants.API_URL
                }

                contentType(type = ContentType.Application.Json)
            }

            install(plugin = HttpTimeout) {
                requestTimeoutMillis = 10 * 1000
            }

            install(plugin = HttpCache) {
                publicStorage(storage = FileStorage(directory = context.cacheDir))
            }

            install(plugin = ContentNegotiation) {
                json(
                    json =
                        Json {
                            ignoreUnknownKeys = true
                            coerceInputValues = true
                            prettyPrint = true
                        },
                )
            }

            install(plugin = Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.ALL
                logger =
                    object : Logger {
                        override fun log(message: String) {
                            Log.i("KtorClient", message)
                        }
                    }
            }

        }

        return client
    }
}