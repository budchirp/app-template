package me.budchirp.app.android.presentation.views

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import me.budchirp.app.android.data.remote.error.APIResult
import me.budchirp.app.android.data.remote.error.APIResult.Success
import me.budchirp.app.android.data.remote.model.PostModel
import me.budchirp.app.android.presentation.composables.ListItem
import me.budchirp.app.android.presentation.composables.PullToRefreshLazyColumn
import me.budchirp.app.android.presentation.motion.slideInY
import me.budchirp.app.android.presentation.motion.slideOutY
import me.budchirp.app.android.viewmodel.HomeViewModel

@Composable
fun HomeView(viewModel: HomeViewModel = hiltViewModel()) {
    val posts: APIResult<List<PostModel>>? by
        viewModel.postsFlow.collectAsStateWithLifecycle(initialValue = null)

    when (posts) {
        is Success -> {
            val state: MutableTransitionState<Boolean> =
                remember {
                    MutableTransitionState(false).apply {
                        targetState = true
                    }
                }

            AnimatedVisibility(
                visibleState = state,
                enter = slideInY(),
                exit = slideOutY(),
            ) {
                PullToRefreshLazyColumn(
                    modifier =
                        Modifier
                            .fillMaxSize(),
                    onRefresh = { viewModel.fetchPosts() },
                ) {
                    items((posts as Success<List<PostModel>>).data) { post: PostModel ->

                        ListItem(
                            title = post.title,
                            description = post.body,
                            firstItem = {
                                Text(
                                    text = post.id.toString(),
                                )
                            },
                        )
                    }
                }
            }
        }

        is APIResult.Fatal, is APIResult.Error -> {
            LazyColumn(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .wrapContentSize(align = Alignment.Center),
                verticalArrangement = Arrangement.spacedBy(space = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                item {
                    Text(
                        text = "An error occurred while fetching the API",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 16.dp),
                    )
                }

                item {
                    Button(onClick = { viewModel.fetchPosts() }) {
                        Text(text = "Try again")
                    }
                }
            }
        }

        else -> {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        }
    }
}