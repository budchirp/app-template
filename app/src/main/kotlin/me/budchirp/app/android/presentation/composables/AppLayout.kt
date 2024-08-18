package me.budchirp.app.android.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DrawerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import kotlinx.coroutines.CoroutineScope

@Composable
fun AppLayout(
    navController: NavHostController,
    content: @Composable (drawerState: DrawerState, drawerScope: CoroutineScope) -> Unit,
) {
    Surface(
        modifier =
            Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.surface),
    ) {
        Drawer(
            navController = navController,
        ) { drawerState: DrawerState, drawerScope: CoroutineScope ->
            content(drawerState, drawerScope)
        }
    }
}