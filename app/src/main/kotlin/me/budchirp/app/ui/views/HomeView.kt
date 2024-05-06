package me.budchirp.app.ui.views

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import me.budchirp.app.data.remote.model.APIResult
import me.budchirp.app.data.remote.model.PostModel
import me.budchirp.app.ui.composables.Layout
import me.budchirp.app.ui.composables.ListItem
import me.budchirp.app.ui.composables.PullToRefreshLazyColumn
import me.budchirp.app.ui.motion.slideInY
import me.budchirp.app.ui.motion.slideOutY
import me.budchirp.app.ui.navigation.Route

@Composable
fun HomeView(
    navController: NavHostController,
    innerPadding: PaddingValues,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val posts: APIResult<List<PostModel>>? =
        viewModel.postsFlow.collectAsStateWithLifecycle(initialValue = null).value

    Layout(
        navController = navController,
        title = stringResource(id = Route.Home.title),
        innerPadding = innerPadding,
        showBack = false
    ) {
        when (posts) {
            is APIResult.Success -> {
                val state = remember {
                    MutableTransitionState(false).apply {
                        targetState = true
                    }
                }

                AnimatedVisibility(
                    visibleState = state,
                    enter = slideInY(),
                    exit = slideOutY()
                ) {
                    PullToRefreshLazyColumn(
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(space = 4.dp),
                        onRefresh = { viewModel.fetchPosts() }
                    ) {
                        items(posts.data) { post ->

                            ListItem(
                                title = post.title,
                                description = post.body,
                                firstItem = {
                                    Text(
                                        text = post.id.toString()
                                    )
                                })
                        }
                    }
                }
            }

            is APIResult.Fatal, is APIResult.Error -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(align = Alignment.Center),
                    verticalArrangement = Arrangement.spacedBy(space = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    item {
                        Text(
                            text = "An error occurred while fetching the API",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.error,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(horizontal = 16.dp)
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
}