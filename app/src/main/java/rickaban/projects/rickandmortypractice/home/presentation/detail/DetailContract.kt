package rickaban.projects.rickandmortypractice.home.presentation.detail

import rickaban.projects.rickandmortypractice.home.domain.Entry

sealed interface DetailState {
    data object Loading : DetailState
    data class Success(val entry: Entry) : DetailState
    data class Error(val message: String) : DetailState
}

sealed interface DetailIntent {
    data object RetryClicked : DetailIntent
    data object BackClicked : DetailIntent
}

sealed interface DetailEffect {
    data object NavigateBack : DetailEffect
    data class ShowToast(val message: String) : DetailEffect
}