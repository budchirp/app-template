package dev.cankolay.app.android.presentation.view.settings.appearance

import android.os.Build
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.materialkolor.hct.Hct
import com.materialkolor.rememberDynamicColorScheme
import dev.cankolay.app.android.domain.model.application.MaterialYou
import dev.cankolay.app.android.domain.model.application.SettingsState
import dev.cankolay.app.android.presentation.R
import dev.cankolay.app.android.presentation.composable.Card
import dev.cankolay.app.android.presentation.composable.CardStack
import dev.cankolay.app.android.presentation.composable.Icon
import dev.cankolay.app.android.presentation.composable.ListItem
import dev.cankolay.app.android.presentation.composable.layout.AppLayout
import dev.cankolay.app.android.presentation.composable.layout.AppLazyColumn
import dev.cankolay.app.android.presentation.navigation.Route
import dev.cankolay.app.android.presentation.theme.isDark
import dev.cankolay.app.android.presentation.viewmodel.SettingsEvent
import dev.cankolay.app.android.presentation.viewmodel.SettingsViewModel

private val ColorList =
    ((4..10) + (1..3)).map { it * 35.0 }.map { Color(Hct.from(it, 40.0, 40.0).toInt()) }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MaterialYouView(settingsViewModel: SettingsViewModel = hiltViewModel()) {
    AppLayout(route = Route.MaterialYou) {
        val state by settingsViewModel.state.collectAsState()

        state?.let { state ->
            AppLazyColumn {
                item {
                    CardStack(
                        modifier =
                            Modifier
                                .padding(horizontal = 16.dp),
                        color = MaterialTheme.colorScheme.primaryContainer,
                        items = listOf({
                            val onClick: (Boolean) -> Unit = { materialYou ->
                                settingsViewModel.onEvent(
                                    event = SettingsEvent.UpdateSettings(
                                        settingsState = state.copy(
                                            materialYou = if (materialYou) MaterialYou.SEED(color = ColorList.first()) else MaterialYou.OFF
                                        )
                                    )
                                )
                            }

                            ListItem(
                                title = stringResource(id = R.string.material_you),
                                trailingContent = {
                                    Switch(
                                        checked = state.materialYou != MaterialYou.OFF,
                                        onCheckedChange = onClick
                                    )
                                },
                                onClick = { onClick(state.materialYou == MaterialYou.OFF) }
                            )
                        })
                    )
                }

                item {
                    val colorsPerPage = 4
                    val pagerState =
                        rememberPagerState { (ColorList.size + colorsPerPage - 1) / colorsPerPage }

                    HorizontalPager(
                        state = pagerState,
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        pageSpacing = 16.dp
                    ) { page ->
                        val start = page * colorsPerPage
                        val end = minOf(start + colorsPerPage, ColorList.size)

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(
                                8.dp,
                                alignment = Alignment.CenterHorizontally
                            )
                        ) {
                            for (index in start until end) {
                                ColorButton(
                                    state = state,
                                    selected = state.materialYou is MaterialYou.SEED && ColorList.indexOf(
                                        element = state.materialYou.color
                                    ) == index,
                                    color = ColorList[index],
                                    onClick = {
                                        settingsViewModel.onEvent(
                                            SettingsEvent.UpdateSettings(
                                                settingsState = state.copy(
                                                    materialYou = MaterialYou.SEED(
                                                        color = ColorList[index]
                                                    )
                                                )
                                            )
                                        )
                                    }
                                )
                            }

                            repeat(times = colorsPerPage - (end - start)) {
                                Surface(
                                    modifier = Modifier
                                        .sizeIn(maxWidth = 96.dp, maxHeight = 96.dp)
                                ) {
                                    Spacer(
                                        modifier = Modifier
                                            .weight(weight = 1f)
                                            .height(height = 1.dp)
                                    )
                                }
                            }
                        }
                    }
                }

                item {
                    CardStack(
                        modifier =
                            Modifier
                                .padding(horizontal = 16.dp),
                        items = listOf({
                            val onClick = { useWallpaperColors: Boolean ->
                                settingsViewModel.onEvent(
                                    event = SettingsEvent.UpdateSettings(
                                        settingsState = state.copy(
                                            materialYou = if (useWallpaperColors) MaterialYou.WALLPAPER else MaterialYou.SEED(
                                                color = ColorList.first()
                                            )
                                        )
                                    )
                                )
                            }

                            val isEnabled = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S

                            ListItem(
                                title = stringResource(id = R.string.wallpaper_colors),
                                description = if (!isEnabled) stringResource(id = R.string.material_you_desc_unsupported) else null,
                                trailingContent = {
                                    Switch(
                                        checked = isEnabled && state.materialYou is MaterialYou.WALLPAPER,
                                        enabled = isEnabled,
                                        onCheckedChange = onClick,
                                    )
                                },
                                enabled = isEnabled,
                                onClick = { onClick(state.materialYou !is MaterialYou.WALLPAPER) }
                            )
                        })
                    )
                }
            }
        }
    }
}

@Composable
private fun RowScope.ColorButton(
    state: SettingsState,
    selected: Boolean,
    color: Color,
    onClick: (Color) -> Unit = {},
) {
    Surface(
        modifier = Modifier
            .sizeIn(maxWidth = 96.dp, maxHeight = 96.dp)
    ) {
        Card(
            modifier = Modifier
                .weight(weight = 1f)
                .aspectRatio(ratio = 1f),
            contentPadding = PaddingValues(all = 16.dp),
            color = MaterialTheme.colorScheme.surfaceContainer,
            shape = MaterialTheme.shapes.medium,
            onClick = {
                onClick(color)
            }
        ) {
            val colorScheme =
                rememberDynamicColorScheme(seedColor = color, isDark = isDark(state = state))

            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .align(alignment = Alignment.Center),
                    color = colorScheme.primaryContainer,
                    shape = CircleShape
                ) {
                    Row {
                        Surface(
                            modifier = Modifier
                                .weight(weight = 1f)
                                .aspectRatio(ratio = 1f),
                            color = colorScheme.onSecondaryContainer
                        ) {}

                        Surface(
                            modifier = Modifier
                                .weight(weight = 1f)
                                .aspectRatio(ratio = 1f),
                            color = colorScheme.primary
                        ) {}
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(all = 8.dp)
                        .align(Alignment.Center)
                ) {
                    Row {
                        AnimatedVisibility(
                            visible = selected,
                            enter = fadeIn() + scaleIn(),
                            exit = fadeOut() + scaleOut()
                        ) {
                            Surface(
                                modifier = Modifier.fillMaxSize(),
                                color = MaterialTheme.colorScheme.surface,
                                shape = CircleShape
                            ) {
                                Icon(icon = Icons.Default.Check)
                            }
                        }
                    }
                }
            }
        }
    }
}