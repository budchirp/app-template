package me.budchirp.app.android.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import kotlinx.coroutines.CoroutineScope

@Composable
fun AppLayout(
    navController: NavHostController,
    content: @Composable (drawerState: DrawerState, drawerScope: CoroutineScope) -> Unit
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Open)
    val drawerScope = rememberCoroutineScope()

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surface)
    ) {
        Drawer(
            navController = navController,
            drawerState = drawerState,
            drawerScope = drawerScope
        ) {
            content(drawerState = drawerState, drawerScope = drawerScope)
        }
    }
}