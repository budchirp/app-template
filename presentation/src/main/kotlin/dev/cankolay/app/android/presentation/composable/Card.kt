package dev.cankolay.app.android.presentation.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import sv.lib.squircleshape.SquircleShape

@Composable
fun Card(
    modifier: Modifier = Modifier,
    shape: Shape = MaterialTheme.shapes.large,
    color: Color = MaterialTheme.colorScheme.surfaceContainer,
    contentPadding: PaddingValues = PaddingValues(all = 16.dp),
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    onClick: (() -> Unit)? = null,
    content: @Composable () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .then(other = modifier)
            .clip(shape = shape)
            .then(other = if (onClick != null) Modifier.clickable { onClick() } else Modifier),
        shape = shape,
        colors = CardDefaults.cardColors(
            containerColor = color
        )) {
        Column(
            modifier = Modifier.padding(paddingValues = contentPadding),
            verticalArrangement = verticalArrangement,
            horizontalAlignment = horizontalAlignment
        ) {
            content()
        }
    }
}

@Composable
fun CardStack(
    modifier: Modifier = Modifier,
    shape: Shape = MaterialTheme.shapes.large,
    color: Color = MaterialTheme.colorScheme.surfaceContainer,
    items: List<@Composable () -> Unit>
) {
    if (items.isEmpty()) return

    Card(
        modifier = modifier,
        shape = shape,
        color = Color.Transparent,
        contentPadding = PaddingValues(all = 0.dp),
        verticalArrangement = Arrangement.spacedBy(space = 2.dp)
    ) {
        items.forEach { item ->
            Card(
                shape = SquircleShape(radius = 4.dp),
                color = color,
                contentPadding = PaddingValues(all = 0.dp)
            ) {
                item()
            }
        }
    }
}

data class CardStackListItem(
    val title: String,
    val description: String? = null,
    val onClick: (() -> Unit)? = null,
    val enabled: Boolean = onClick != null,
    val leadingContent: @Composable (() -> Unit)? = null,
    val trailingContent: @Composable (() -> Unit)? = null,
)

@Composable
fun CardStackList(
    modifier: Modifier = Modifier,
    shape: Shape = MaterialTheme.shapes.large,
    color: Color = MaterialTheme.colorScheme.surfaceContainer,
    items: List<CardStackListItem>
) {
    CardStack(
        modifier = modifier,
        shape = shape,
        color = color,
        items = items.map { item ->
            {
                ListItem(
                    title = item.title,
                    description = item.description,
                    leadingContent = item.leadingContent,
                    trailingContent = item.trailingContent,
                    onClick = item.onClick,
                    enabled = item.enabled,
                )
            }
        }
    )
}
