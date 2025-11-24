package dev.cankolay.app.android.presentation.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
    overline: String? = null,
    onClick: (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    contentPadding: PaddingValues = PaddingValues(
        horizontal = 16.dp,
        vertical = 12.dp
    ),
    separator: Boolean = false,
    trailingIcon: @Composable (() -> Unit)? = null,
    enabled: Boolean = onClick != null,
) {
    Column(modifier = containerModifier) {
        Row(
            modifier =
                modifier
                    .then(
                        other = if (onClick != null && enabled) {
                            Modifier.clickable { onClick() }
                        } else {
                            Modifier
                        },
                    )
                    .padding(
                        paddingValues = contentPadding
                    )
                    .then(other = modifier),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(space = 16.dp)
        ) {
            leadingIcon?.let {
                Column(
                    modifier = Modifier
                        .size(size = 24.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    leadingIcon()
                }
            }

            Column(
                modifier =
                    Modifier
                        .fillMaxWidth()
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

            trailingIcon?.let {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(space = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    trailingIcon()
                }
            }
        }

        if (separator) {
            HorizontalDivider()
        }
    }
}