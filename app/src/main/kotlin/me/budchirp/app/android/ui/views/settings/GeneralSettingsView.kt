package me.budchirp.app.android.ui.views.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun GeneralSettingsView() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(space = 4.dp)
    ) {
        items(100) { index ->
            Text(
                text = "General Setting ${index + 1}",
                Modifier.padding(horizontal = 16.dp)
            )
        }
    }
}