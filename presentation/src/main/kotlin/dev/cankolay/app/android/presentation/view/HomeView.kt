package dev.cankolay.app.android.presentation.view

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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import dev.cankolay.app.android.domain.model.api.ApiResult
import dev.cankolay.app.android.domain.model.api.post.Post
import dev.cankolay.app.android.presentation.composable.ListItem
import dev.cankolay.app.android.presentation.composable.PullToRefreshLazyColumn
import dev.cankolay.app.android.presentation.composable.layout.AppLayout
import dev.cankolay.app.android.presentation.motion.slideInY
import dev.cankolay.app.android.presentation.motion.slideOutY
import dev.cankolay.app.android.presentation.navigation.Route
import dev.cankolay.app.android.presentation.viewmodel.PostViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView(postViewModel: PostViewModel = hiltViewModel()) {
    AppLayout(route = Route.Home) {
        val posts by postViewModel.posts.collectAsState()


        when (posts) {
            is ApiResult.Success -> {
                AnimatedVisibility(
                    visibleState = remember {
                        MutableTransitionState(initialState = false).apply {
                            targetState = true
                        }
                    },
                    enter = slideInY(),
                    exit = slideOutY(),
                ) {
                    PullToRefreshLazyColumn(
                        modifier =
                            Modifier
                                .fillMaxSize(),
                        onRefresh = { },
                    ) {
                        items(items = (posts as ApiResult.Success<List<Post>>).data) { post ->
                            ListItem(
                                title = post.title,
                                description = post.description,
                                leadingIcon = {
                                    Text(
                                        text = post.id.toString(),
                                    )
                                },
                            )
                        }
                    }
                }
            }

            is ApiResult.Fatal, is ApiResult.Error -> {
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
                        Button(onClick = { }) {
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
}