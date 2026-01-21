package dev.cankolay.app.android.presentation.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.cankolay.app.android.presentation.composable.layout.AppLazyColumn

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun PullToRefreshLazyColumn(
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
    contentPadding: PaddingValues = PaddingValues(vertical = 16.dp),
    verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(space = 16.dp),
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    onRefresh: () -> Unit,
    isLoading: Boolean = false,
    content: LazyListScope.() -> Unit,
) {
    PullToRefreshBox(
        onRefresh = onRefresh,
        isRefreshing = isLoading,
    ) {
        AppLazyColumn(
            modifier = modifier,
            state = state,
            contentPadding = contentPadding,
            verticalArrangement = verticalArrangement,
            horizontalAlignment = horizontalAlignment
        ) {
            content()
        }
    }
}