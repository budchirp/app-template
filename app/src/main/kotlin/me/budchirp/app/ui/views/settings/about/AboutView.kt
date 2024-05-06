package me.budchirp.app.ui.views.settings.about

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import me.budchirp.app.R
import me.budchirp.app.ui.composables.Icon
import me.budchirp.app.ui.composables.Layout
import me.budchirp.app.ui.composables.ListItem
import me.budchirp.app.ui.navigation.Route
import me.budchirp.app.ui.navigation.aboutRoutes

@Composable
fun AboutView(navController: NavHostController) {
    Layout(
        navController = navController,
        title = stringResource(id = Route.About.title)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(space = 16.dp)
                ) {
                    AppCard()

                    DevCard()
                }
            }

            items(aboutRoutes) { route ->
                ListItem(
                    title = stringResource(id = route.title),
                    description = stringResource(id = route.description),
                    firstItem = {
                        Icon(
                            icon = route.icon
                        )
                    }
                ) { navController.navigate(route = route.destination) }
            }
        }
    }
}

@Composable
fun AppCard() {
    val context = LocalContext.current

    val activityLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.StartActivityForResult()
        ) {}

    val version =
        context.packageManager.getPackageInfo(context.packageName, 0)?.versionName ?: "1.0.0"
    val icon = context.packageManager.getApplicationIcon(context.packageName)

    val appGithubUrl = stringResource(id = R.string.app_github_url)

    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = icon,
                contentDescription = null,
                modifier = Modifier
                    .clip(shape = CircleShape)
                    .size(size = 64.dp)
                    .padding(end = 16.dp)
            )

            Column {
                Text(
                    text = stringResource(id = R.string.app_name),
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = version,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }

        HorizontalDivider(
            thickness = 1.dp
        )

        ListItem(
            title = stringResource(id = R.string.about_view_on, "Github"),
            description = stringResource(
                id = R.string.about_view_on_desc,
                "Github"
            ),
            firstItem = {
                Icon(icon = painterResource(id = R.drawable.ic_github))
            }
        ) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(appGithubUrl))
            activityLauncher.launch(intent)
        }
    }
}

@Composable
fun DevCard() {
    val activityLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.StartActivityForResult()
        ) {}

    val devWebsiteUrl = stringResource(id = R.string.dev_website_url)
    val devGithubUrl = stringResource(id = R.string.dev_github_url)
    val devXUrl = stringResource(id = R.string.dev_x_url)

    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.dev),
                style = MaterialTheme.typography.titleLarge
            )
        }

        HorizontalDivider(
            thickness = 1.dp
        )
        ListItem(
            title = stringResource(id = R.string.dev_name),
            description = "@" + stringResource(id = R.string.dev_username),
            firstItem = {
                Icon(icon = Icons.Default.Person)
            }
        ) {
            val intent =
                Intent(Intent.ACTION_VIEW, Uri.parse(devWebsiteUrl))
            activityLauncher.launch(intent)
        }

        ListItem(
            title = stringResource(id = R.string.about_follow_on, "Github"),
            description = stringResource(
                id = R.string.about_follow_on_desc,
                "Github"
            ),
            firstItem = {
                Icon(icon = painterResource(id = R.drawable.ic_github))
            }
        ) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(devGithubUrl))
            activityLauncher.launch(intent)
        }

        ListItem(
            title = stringResource(id = R.string.about_follow_on, "X"),
            description = stringResource(
                id = R.string.about_follow_on_desc,
                "X"
            ),
            firstItem = {
                Icon(icon = painterResource(id = R.drawable.ic_x))
            }
        ) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(devXUrl))
            activityLauncher.launch(intent)
        }
    }
}