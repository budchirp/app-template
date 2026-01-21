package dev.cankolay.app.android.presentation.composable.layout

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextOverflow
import dev.cankolay.app.android.presentation.composable.Icon
import dev.cankolay.app.android.presentation.composition.LocalNavBackStack
import dev.cankolay.app.android.presentation.navigation.Route
import dev.cankolay.app.android.presentation.navigation.getDetails

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopAppBar(
    route: Route,
    scrollBehavior: TopAppBarScrollBehavior,
    leadingContent: @Composable () -> Unit = {},
    trailingContent: @Composable () -> Unit = {},
) {
    val navBackStack = LocalNavBackStack.current

    val details = route.getDetails()

    LargeTopAppBar(
        title = {
            Text(
                text = details.title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        },
        actions = {
            trailingContent()
        },
        navigationIcon = {
            leadingContent()

            val showBack = !routes.any {
                it == route
            }

            if (showBack) {
                FilledIconButton(
                    colors = IconButtonDefaults.filledIconButtonColors(containerColor = MaterialTheme.colorScheme.surfaceContainer),
                    onClick = { navBackStack.removeLastOrNull() }) {
                    Icon(
                        icon = Icons.AutoMirrored.Filled.ArrowBack,
                    )
                }
            }
        },
        windowInsets = WindowInsets.systemBars.only(sides = WindowInsetsSides.Top),
        colors = TopAppBarDefaults.topAppBarColors(scrolledContainerColor = TopAppBarDefaults.topAppBarColors().containerColor),
        scrollBehavior = scrollBehavior
    )
}