package rickaban.projects.rickandmortypractice.home.presentation.home

import rickaban.projects.rickandmortypractice.home.domain.Entry

sealed interface HomeState {
    data object Loading : HomeState
    data class Error(val message: String) : HomeState
    data class Success(
        val entries: List<Entry>,
        val isPaginating: Boolean = false, // True when fetching page 2, 3, etc.
        val endReached: Boolean = false    // True when the API says "next" is null
    ) : HomeState
}

// In your HomeIntent:
sealed interface HomeIntent {
    data object LoadEntries : HomeIntent
    data object LoadNextPage : HomeIntent
    data object RefreshEntries : HomeIntent
    data class EntryClicked(val entryId: Long) : HomeIntent
    data object RetryClicked : HomeIntent
}

sealed interface HomeEffect {
    data class NavigateToDetail(val entryId: Long) : HomeEffect
    data class ShowToast(val message: String) : HomeEffect
}