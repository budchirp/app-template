@file:OptIn(ExperimentalMaterial3Api::class)

package me.budchirp.app.ui.views.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import me.budchirp.app.ui.components.Layout
import me.budchirp.app.ui.navigation.Route
import me.budchirp.app.ui.navigation.settingRoutes

@Composable
fun SettingsView(navController: NavHostController) {
  Layout(
      navController = navController,
      title = stringResource(id = Route.Settings.label),
      showBack = true
  ) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
      items(settingRoutes) { route ->
        Row(
            modifier =
                Modifier.fillMaxWidth().clickable { navController.navigate(route.destination) }
        ) {
          ListItem(
              headlineContent = {
                Text(
                    text = stringResource(id = route.label),
                    style = MaterialTheme.typography.titleMedium
                )
              },
              supportingContent = { Text(text = stringResource(id = route.description)) },
              leadingContent = {
                Icon(
                    imageVector = route.icon,
                    contentDescription = stringResource(id = route.description)
                )
              }
          )
        }
      }
    }
  }
}
