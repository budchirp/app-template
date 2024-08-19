package me.budchirp.app.android.ui.composables

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PullToRefreshLazyColumn(
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
    onRefresh: () -> Unit,
    content: LazyListScope.() -> Unit,
) {
    PullToRefreshBox(
        state = rememberPullToRefreshState(),
        onRefresh = onRefresh,
        isRefreshing = false,
    ) {
        LazyColumn(
            modifier = modifier,
            state = state,
        ) {
            content()
        }
    }
}