package me.budchirp.app.android.data.datastore.model

import me.budchirp.app.android.presentation.theme.Theme

data class Settings(
    val exampleField: String,
    val theme: Theme,
    val materialYou: Boolean,
)

data class NullableSettings(
    val exampleField: String? = null,
    val theme: Theme? = null,
    val materialYou: Boolean? = null,
)