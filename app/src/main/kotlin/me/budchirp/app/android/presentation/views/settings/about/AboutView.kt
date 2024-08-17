package me.budchirp.app.android.presentation.views.settings.about

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
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
import me.budchirp.app.android.R
import me.budchirp.app.android.presentation.composables.Card
import me.budchirp.app.android.presentation.composables.Icon
import me.budchirp.app.android.presentation.composables.ListItem
import me.budchirp.app.android.presentation.navigation.Route
import me.budchirp.app.android.presentation.navigation.aboutRoutes

@Composable
fun AboutView(navController: NavHostController) {
    LazyColumn(
        modifier =
            Modifier
                .fillMaxSize(),
    ) {
        item {
            Column(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(space = 16.dp),
            ) {
                AppCard()

                DevCard()
            }
        }

        items(aboutRoutes) { route: Route ->
            ListItem(
                title = stringResource(id = route.title),
                description = stringResource(id = route.description),
                onClick = { navController.navigate(route = route.destination) },
                firstItem = {
                    Icon(
                        icon = route.icon,
                    )
                },
            )
        }
    }
}

@Composable
fun AppCard() {
    val context: Context = LocalContext.current

    val activityLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.StartActivityForResult(),
        ) {}

    val version: String =
        context.packageManager.getPackageInfo(context.packageName, 0)?.versionName ?: "1.0.0"
    val icon: Drawable = context.packageManager.getApplicationIcon(context.packageName)

    val appGithubUrl: String = stringResource(id = R.string.app_github_url)

    Card(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
    ) {
        Row(
            modifier = Modifier.padding(all = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AsyncImage(
                model = icon,
                contentDescription = null,
                modifier =
                    Modifier
                        .clip(shape = CircleShape)
                        .size(size = 64.dp)
                        .padding(end = 16.dp),
            )

            Column {
                Text(
                    text = stringResource(id = R.string.app_name),
                    style = MaterialTheme.typography.titleLarge,
                )
                Text(
                    text = version,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }

        HorizontalDivider()

        ListItem(
            title = stringResource(id = R.string.about_view_on, "Github"),
            description =
                stringResource(
                    id = R.string.about_view_on_desc,
                    "Github",
                ),
            onClick = {
                activityLauncher.launch(Intent(Intent.ACTION_VIEW, Uri.parse(appGithubUrl)))
            },
            firstItem = {
                Icon(icon = painterResource(id = R.drawable.ic_github))
            },
        )
    }
}

@Composable
fun DevCard() {
    val activityLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.StartActivityForResult(),
        ) {}

    val devWebsiteUrl: String = stringResource(id = R.string.dev_website_url)
    val devGithubUrl: String = stringResource(id = R.string.dev_github_url)
    val devXUrl: String = stringResource(id = R.string.dev_x_url)

    Card(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
    ) {
        Row(
            modifier = Modifier.padding(all = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(id = R.string.dev),
                style = MaterialTheme.typography.titleLarge,
            )
        }

        HorizontalDivider()

        ListItem(
            title = stringResource(id = R.string.dev_name),
            description = "@" + stringResource(id = R.string.dev_username),
            onClick = {
                activityLauncher.launch(Intent(Intent.ACTION_VIEW, Uri.parse(devWebsiteUrl)))
            },
            firstItem = {
                Icon(icon = Icons.Default.Person)
            },
        )

        ListItem(
            title = stringResource(id = R.string.about_follow_on, "Github"),
            description =
                stringResource(
                    id = R.string.about_follow_on_desc,
                    "Github",
                ),
            onClick = {
                activityLauncher.launch(Intent(Intent.ACTION_VIEW, Uri.parse(devGithubUrl)))
            },
            firstItem = {
                Icon(icon = painterResource(id = R.drawable.ic_github))
            },
        )

        ListItem(
            title = stringResource(id = R.string.about_follow_on, "X"),
            description =
                stringResource(
                    id = R.string.about_follow_on_desc,
                    "X",
                ),
            onClick = {
                activityLauncher.launch(Intent(Intent.ACTION_VIEW, Uri.parse(devXUrl)))
            },
            firstItem = {
                Icon(icon = painterResource(id = R.drawable.ic_x))
            },
        )
    }
}