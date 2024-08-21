package me.budchirp.app.android.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.WindowWidthSizeClass
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import me.budchirp.app.android.ui.composition.LocalDrawerState
import me.budchirp.app.android.ui.composition.LocalNavController
import me.budchirp.app.android.ui.navigation.Route
import me.budchirp.app.android.ui.navigation.mainRoutes
import me.budchirp.app.android.viewmodel.AppViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Layout(
    appViewModel: AppViewModel = hiltViewModel<AppViewModel>(),
    route: Route,
    content: @Composable () -> Unit,
) {
    val navController: NavHostController = LocalNavController.current
    val drawerState: DrawerState = LocalDrawerState.current

    val coroutineScope: CoroutineScope = rememberCoroutineScope()
    val isDrawerEnabled: Boolean by appViewModel.isDrawerEnabled.collectAsStateWithLifecycle()

    val scrollBehavior: TopAppBarScrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(
            state = rememberTopAppBarState(),
            canScroll = { true },
        )

    val windowInsets: WindowInsets =
        WindowInsets.statusBars.only(sides = WindowInsetsSides.Top)

    Scaffold(
        contentWindowInsets = windowInsets,
        modifier =
            Modifier
                .fillMaxSize()
                .nestedScroll(connection = scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                windowInsets = windowInsets,
                title = {
                    Text(
                        text = stringResource(id = route.title),
                        style = MaterialTheme.typography.titleLarge,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                },
                navigationIcon = {
                    var showBack = false
                    var showMenu = true
                    run stop@{
                        mainRoutes.forEach { _route: Route ->
                            if (route.destination == _route.destination) {
                                showBack = false
                                showMenu = isDrawerEnabled

                                return@stop
                            } else {
                                showBack = true
                                showMenu = false
                            }
                        }
                    }

                    val windowSizeClass: WindowSizeClass =
                        currentWindowAdaptiveInfo().windowSizeClass
                    if (windowSizeClass.windowWidthSizeClass ==
                        WindowWidthSizeClass.EXPANDED
                    ) {
                        showMenu =
                            false
                    }

                    if (showBack) {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                icon = Icons.AutoMirrored.Filled.ArrowBack,
                            )
                        }
                    }

                    if (showMenu) {
                        IconButton(onClick = {
                            coroutineScope.launch {
                                drawerState.open()
                            }
                        }) {
                            Icon(
                                icon = Icons.Filled.Menu,
                            )
                        }
                    }
                },
                scrollBehavior = scrollBehavior,
            )
        },
        containerColor = MaterialTheme.colorScheme.surface,
    ) { innerPadding: PaddingValues ->
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(
                        paddingValues = innerPadding,
                    ),
        ) { content() }
    }
}