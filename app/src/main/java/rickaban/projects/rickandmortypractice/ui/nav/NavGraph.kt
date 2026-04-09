package rickaban.projects.rickandmortypractice.ui.nav

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import rickaban.projects.rickandmortypractice.home.presentation.detail.DetailHost
import rickaban.projects.rickandmortypractice.home.presentation.home.HomeHost

@Composable
fun NavGraph() {
    val navigator = rememberAppNavigator(initialRoute = Home)

    NavDisplay(
        backStack = navigator.entries,
        entryProvider = entryProvider(
            fallback = { key ->
                NavEntry(key) { Text("Unknown") }
            }
        ) {
            entry<Home> {
                HomeHost(
                    onNavigateToDetail = { entryId ->
                        navigator.navigateTo(Detail(entryId))
                    }
                )
            }
            entry<Detail> { detail ->
                DetailHost(
                    entryId = detail.entryId,
                    onNavigateBack = navigator::goBack
                )
            }
        }
    )
}