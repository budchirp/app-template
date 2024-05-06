package me.budchirp.app.ui.views.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import me.budchirp.app.ui.composables.Layout
import me.budchirp.app.ui.navigation.Route

@Composable
fun GeneralSettingsView(navController: NavHostController) {
    Layout(
        navController = navController,
        title = stringResource(id = Route.GeneralSettings.title)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(space = 4.dp)
        ) {
            items(100) { index ->
                Text(
                    text = "General Setting ${index + 1}",
                    Modifier.padding(horizontal = 16.dp)
                )
            }
        }
    }
}