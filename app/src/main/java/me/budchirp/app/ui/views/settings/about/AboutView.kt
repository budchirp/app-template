@file:OptIn(ExperimentalMaterial3Api::class)

package me.budchirp.app.ui.views.settings.about

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import me.budchirp.app.R
import me.budchirp.app.ui.components.Layout
import me.budchirp.app.ui.navigation.Route
import me.budchirp.app.ui.navigation.aboutRoutes

@Composable
fun AboutView(navController: NavHostController) {
  val context = LocalContext.current

  val version = context.packageManager.getPackageInfo(context.packageName, 0).versionName

  Layout(
      navController = navController,
      title = stringResource(id = Route.About.label),
      showBack = true
  ) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
      item {
        Column(
            modifier = Modifier.fillMaxWidth().padding(bottom = 4.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
          Text(
              text = stringResource(id = R.string.app_name),
              style = MaterialTheme.typography.titleLarge
          )
          Text(text = version)
        }
      }

      items(aboutRoutes) { route ->
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
