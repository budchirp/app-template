package dev.cankolay.app.android.presentation.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import dev.cankolay.app.android.domain.model.api.ApiResult
import dev.cankolay.app.android.domain.model.api.post.Post
import dev.cankolay.app.android.presentation.R
import dev.cankolay.app.android.presentation.composable.CardStackList
import dev.cankolay.app.android.presentation.composable.CardStackListItem
import dev.cankolay.app.android.presentation.composable.PullToRefreshLazyColumn
import dev.cankolay.app.android.presentation.composable.layout.AppLayout
import dev.cankolay.app.android.presentation.navigation.route.Route
import dev.cankolay.app.android.presentation.viewmodel.PostEvent
import dev.cankolay.app.android.presentation.viewmodel.PostViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun HomeView(postViewModel: PostViewModel = hiltViewModel()) {
    val posts by postViewModel.posts.collectAsState()
    LaunchedEffect(key1 = Unit) {
        postViewModel.onEvent(event = PostEvent.FetchPosts)
    }

    AppLayout(route = Route.Home) {
        PullToRefreshLazyColumn(
            isLoading = posts is ApiResult.Loading,
            onRefresh = { postViewModel.onEvent(event = PostEvent.FetchPosts) },
        ) {
            when (posts) {
                is ApiResult.Success -> {
                    item {
                        CardStackList(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            items = (posts as ApiResult.Success<List<Post>>).data.map { post ->
                                CardStackListItem(
                                    title = post.title,
                                    description = post.description,
                                )
                            }
                        )
                    }
                }

                is ApiResult.Fatal, is ApiResult.Error -> {
                    item {
                        Column(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            verticalArrangement = Arrangement.spacedBy(space = 16.dp)
                        ) {
                            Text(
                                text = stringResource(id = R.string.api_error),
                                style = MaterialTheme.typography.titleLarge,
                                color = MaterialTheme.colorScheme.error,
                            )

                            Button(onClick = { postViewModel.onEvent(event = PostEvent.FetchPosts) }) {
                                Text(text = stringResource(id = R.string.try_again))
                            }
                        }
                    }
                }

                else -> {}
            }
        }
    }
}