@file:OptIn(ExperimentalMaterial3Api::class)

package me.budchirp.app.ui.views.settings

import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.core.app.LocaleManagerCompat
import androidx.core.os.LocaleListCompat
import androidx.navigation.NavHostController
import me.budchirp.app.R
import me.budchirp.app.ui.components.Layout
import me.budchirp.app.ui.navigation.Route

@Composable
fun LanguagesView(navController: NavHostController) {
  val context = LocalContext.current

  val languages =
      mapOf(R.string.en to "en", R.string.tr to "tr").mapKeys { language ->
        stringResource(id = language.key)
      }

  val _currentLanguage =
      AppCompatDelegate.getApplicationLocales()[0]
          ?: LocaleManagerCompat.getSystemLocales(context)[0]
  val currentLanguage = _currentLanguage?.toLanguageTag() ?: "en"

  val selectedLanguage = rememberSaveable { mutableStateOf(currentLanguage) }

  Layout(
      navController = navController,
      title = stringResource(id = Route.Languages.label),
      showBack = true
  ) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
      languages.keys.forEach { language ->
        item {
          Row(
              modifier =
                  Modifier.fillMaxWidth().clickable {
                    selectedLanguage.value = languages[language] ?: ""

                    AppCompatDelegate.setApplicationLocales(
                        LocaleListCompat.forLanguageTags(selectedLanguage.value)
                    )
                  }
          ) {
            ListItem(
                headlineContent = { Text(text = language) },
                leadingContent = {
                  RadioButton(
                      selected = languages[language] == selectedLanguage.value,
                      onClick = {
                        selectedLanguage.value = languages[language] ?: ""

                        AppCompatDelegate.setApplicationLocales(
                            LocaleListCompat.forLanguageTags(selectedLanguage.value)
                        )
                      }
                  )
                }
            )
          }
        }
      }
    }
  }
}
