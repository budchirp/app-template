package me.budchirp.app.android.data.remote

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import me.budchirp.app.android.BuildConfig
import javax.inject.Inject

class KtorClient @Inject constructor() {
    private val client = HttpClient(OkHttp) {
        if (BuildConfig.DEBUG) {
            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.HEADERS
                sanitizeHeader { header -> header == HttpHeaders.Authorization }
                logger = object : Logger {
                    override fun log(message: String) {
                        Log.i("KtorClient", message)
                    }
                }
            }
        }

        install(ContentNegotiation) {
            json(json = Json {
                ignoreUnknownKeys = true
            })
        }
    }

    fun getClient(): HttpClient {
        return client
    }
}