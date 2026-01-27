package dev.cankolay.app.android.presentation.view.settings

import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.core.net.toUri
import coil3.compose.AsyncImage
import dev.cankolay.app.android.presentation.R
import dev.cankolay.app.android.presentation.composable.CardStack
import dev.cankolay.app.android.presentation.composable.Icon
import dev.cankolay.app.android.presentation.composable.ListItem
import dev.cankolay.app.android.presentation.composable.layout.AppLayout
import dev.cankolay.app.android.presentation.composable.layout.AppLazyColumn
import dev.cankolay.app.android.presentation.navigation.route.Route

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutView() {
    AppLayout(route = Route.About) {
        AppLazyColumn {
            item {
                AppCard()
            }

            item {
                DevCard()
            }
        }
    }
}

@Composable
fun AppCard() {
    val context = LocalContext.current

    val activityLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.StartActivityForResult(),
        ) {}

    CardStack(
        modifier =
            Modifier
                .padding(horizontal = 16.dp),
        items = listOf({
            Row(
                modifier = Modifier.padding(all = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(space = 16.dp),
            ) {
                AsyncImage(
                    model = context.packageManager.getApplicationIcon(context.packageName),
                    contentDescription = null,
                    modifier =
                        Modifier
                            .clip(shape = CircleShape)
                            .size(size = 48.dp),
                )

                Column {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        style = MaterialTheme.typography.titleMedium,
                    )

                    Text(
                        text =
                            context.packageManager
                                .getPackageInfo(
                                    context.packageName,
                                    0,
                                )?.versionName ?: "1.0.0",
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }
        }, {
            val appGithubUrl: String = stringResource(id = R.string.app_github)
            ListItem(
                title = stringResource(id = R.string.about_view_on, "Github"),
                description =
                    stringResource(
                        id = R.string.about_view_on_desc,
                        "Github",
                    ),
                onClick = {
                    activityLauncher.launch(Intent(Intent.ACTION_VIEW, appGithubUrl.toUri()))
                },
                leadingContent = {
                    Icon(icon = painterResource(id = R.drawable.ic_github))
                },
            )
        })
    )
}

@Composable
fun DevCard() {
    val activityLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.StartActivityForResult(),
        ) {}

    CardStack(
        modifier =
            Modifier
                .padding(horizontal = 16.dp),
        items =
            listOf({
                val devWebsiteUrl: String = stringResource(id = R.string.dev_website)
                ListItem(
                    title = stringResource(id = R.string.dev_name),
                    description = "@" + stringResource(id = R.string.dev_username),
                    onClick = {
                        activityLauncher.launch(
                            Intent(
                                Intent.ACTION_VIEW,
                                devWebsiteUrl.toUri()
                            )
                        )
                    },
                    leadingContent = {
                        Icon(icon = Icons.Default.Person)
                    },
                )
            }, {
                val devGithubUrl: String = stringResource(id = R.string.dev_github)
                ListItem(
                    title = stringResource(id = R.string.about_follow_on, "Github"),
                    description =
                        stringResource(
                            id = R.string.about_follow_on_desc,
                            "Github",
                        ),
                    onClick = {
                        activityLauncher.launch(
                            Intent(
                                Intent.ACTION_VIEW,
                                devGithubUrl.toUri()
                            )
                        )
                    },
                    leadingContent = {
                        Icon(icon = painterResource(id = R.drawable.ic_github))
                    },
                )
            })
    )
}
