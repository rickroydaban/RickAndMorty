package rickaban.projects.rickandmortypractice.ui.nav

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.NavKey

class Navigator(
    initialRoute: NavKey
) {
    private val _entries = mutableStateListOf(initialRoute)

    val entries: List<NavKey> get() = _entries

    fun navigateTo(route: NavKey) {
        _entries.add(route)
    }

    fun goBack() {
        if(_entries.size > 1) {
            _entries.removeAt(_entries.lastIndex)
        }
    }

    fun isAtRoot() = _entries.size <= 1
}

@Composable
fun rememberAppNavigator(initialRoute: NavKey): Navigator {
    return remember { Navigator(initialRoute) }
}