package me.budchirp.app.android.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun ListItem(
    modifier: Modifier = Modifier,
    title: String,
    description: String? = null,
    onClick: (() -> Unit)? = null,
    firstItem:
        @Composable
        (() -> Unit)? = null,
    lastItem: @Composable (() -> Unit)? = null,
    enabled: Boolean = onClick != null,
) {
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .then(
                    if (
                        onClick != null && enabled
                    ) {
                        Modifier.clickable { onClick() }
                    } else {
                        modifier
                    },
                ).padding(
                    horizontal = 16.dp,
                    vertical = if (!description.isNullOrEmpty()) 16.dp else 12.dp,
                ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (firstItem != null) {
            Row(
                modifier = Modifier.padding(end = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                firstItem()
            }
        }

        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .weight(weight = 1f),
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )

            if (!description.isNullOrEmpty()) {
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }

        if (lastItem != null) {
            Row(
                modifier = Modifier.padding(start = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                lastItem()
            }
        }
    }
}