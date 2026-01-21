package dev.cankolay.app.android.presentation.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ListItem(
    modifier: Modifier = Modifier,
    containerModifier: Modifier = Modifier.fillMaxWidth(),
    title: String,
    description: String? = null,
    onClick: (() -> Unit)? = null,
    enabled: Boolean = onClick != null,
    leadingContent: @Composable (() -> Unit)? = null,
    trailingContent: @Composable (() -> Unit)? = null,
    contentPadding: PaddingValues = PaddingValues(
        vertical = if (description == null) 24.dp else 16.dp
    ),
    separator: Boolean = false,
    overline: String? = null,
) {
    Column(modifier = containerModifier) {
        Row(
            modifier =
                modifier
                    .then(other = if (enabled) Modifier.clickable { onClick?.invoke() } else Modifier)
                    .padding(paddingValues = contentPadding)
                    .then(other = modifier),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            leadingContent?.let {
                Row(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .size(size = 24.dp),
                    horizontalArrangement = Arrangement.spacedBy(
                        space = 8.dp,
                        alignment = Alignment.CenterHorizontally
                    ),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    leadingContent()
                }
            }

            Column(
                modifier =
                    Modifier
                        .padding(
                            start = if (leadingContent == null) 16.dp else 0.dp,
                            end = if (trailingContent == null) 16.dp else 0.dp
                        )
                        .weight(weight = 1f),
                verticalArrangement = Arrangement.Center
            ) {
                if (!overline.isNullOrEmpty()) {
                    Text(
                        text = overline,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.outline
                    )
                }

                Text(text = title, style = MaterialTheme.typography.titleMedium)

                if (!description.isNullOrEmpty()) {
                    Text(
                        text = description,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            trailingContent?.let {
                Row(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .height(height = 24.dp),
                    horizontalArrangement = Arrangement.spacedBy(
                        space = 8.dp,
                        alignment = Alignment.CenterHorizontally
                    ),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    trailingContent()
                }
            }
        }

        if (separator) {
            HorizontalDivider()
        }
    }
}