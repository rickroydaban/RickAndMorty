package rickaban.projects.rickandmortypractice.ui.nav

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data object Home: NavKey

@Serializable
data class Detail(val entryId: Long): NavKey