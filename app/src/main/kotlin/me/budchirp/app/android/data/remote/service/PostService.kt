package me.budchirp.app.android.data.remote.service

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.get
import io.ktor.http.URLProtocol
import io.ktor.http.path
import me.budchirp.app.android.data.remote.client.HttpRoutes
import me.budchirp.app.android.data.remote.client.KtorClient
import me.budchirp.app.android.data.remote.error.APIErrorReason
import me.budchirp.app.android.data.remote.error.APIResult
import me.budchirp.app.android.data.remote.model.PostModel
import javax.inject.Inject

class PostService
    @Inject
    constructor(
        ktorClient: KtorClient,
    ) {
        private val client: HttpClient = ktorClient.getClient()

        suspend fun getPosts(): APIResult<List<PostModel>> {
            try {
                val body: List<PostModel> =
                    client
                        .get {
                            url {
                                protocol = URLProtocol.HTTPS
                                host = HttpRoutes.host
                                path(HttpRoutes.posts)
                            }
                        }.body<List<PostModel>>()

                return APIResult.Success(data = body)
            } catch (e: RedirectResponseException) {
                return APIResult.Error(message = e.message, reason = APIErrorReason.SERVER)
            } catch (e: ClientRequestException) {
                return APIResult.Error(message = e.message, reason = APIErrorReason.USER)
            } catch (e: ServerResponseException) {
                return APIResult.Error(message = e.message, reason = APIErrorReason.SERVER)
            } catch (e: Exception) {
                return APIResult.Fatal(throwable = e)
            }
        }
    }