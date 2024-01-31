package me.budchirp.app.ui.views.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import me.budchirp.app.R
import me.budchirp.app.ui.components.Layout
import me.budchirp.app.ui.navigation.Route

@Composable
fun HomeView(navController: NavHostController) {
  Layout(
      navController = navController,
      title = stringResource(id = Route.Home.label),
      showBack = false,
      actions = {
        IconButton(onClick = { navController.navigate(route = Route.Settings.destination) }) {
          Icon(
              imageVector = Route.Settings.icon,
              contentDescription = stringResource(R.string.settings)
          )
        }
      }
  ) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
      items(100) { index -> Text(text = "Home ${index + 1}") }
    }
  }
}
