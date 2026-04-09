package rickaban.projects.rickandmortypractice.home.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import rickaban.projects.rickandmortypractice.home.domain.Repository

class HomeViewModel(
    private val repository: Repository
) : ViewModel() {

    private val _state = MutableStateFlow<HomeState>(HomeState.Loading)
    val state: StateFlow<HomeState> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<HomeEffect>()
    val effect: SharedFlow<HomeEffect> = _effect.asSharedFlow()
    private var currentPage = 1 // Track the current page

    init {
        processIntent(HomeIntent.LoadEntries)
    }

    fun processIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.LoadEntries -> loadFirstPage()
            is HomeIntent.RefreshEntries -> loadFirstPage()
            is HomeIntent.LoadNextPage -> loadNextPage()
            is HomeIntent.RetryClicked -> loadFirstPage()
            is HomeIntent.EntryClicked -> {
                viewModelScope.launch {
                    _effect.emit(HomeEffect.NavigateToDetail(intent.entryId))
                }
            }
        }
    }

    private fun loadFirstPage() {
        viewModelScope.launch {
            _state.value = HomeState.Loading
            currentPage = 1
            try {
                val (newEntries, hasNext) = repository.getEntries(currentPage)
                _state.value = HomeState.Success(
                    entries = newEntries,
                    isPaginating = false,
                    endReached = !hasNext
                )
            } catch (e: Exception) {
                _state.value = HomeState.Error(e.message ?: "Unknown Error")
            }
        }
    }

    private fun loadNextPage() {
        val currentState = _state.value
        // Only paginate if we are currently in Success state, not already paginating, and haven't reached the end
        if (currentState is HomeState.Success && !currentState.isPaginating && !currentState.endReached) {
            viewModelScope.launch {
                // 1. Update state to show the pagination spinner
                _state.value = currentState.copy(isPaginating = true)

                try {
                    // 2. Increment page and fetch
                    currentPage++
                    val (newEntries, hasNext) = repository.getEntries(currentPage)

                    // 3. Append the new data to the old data
                    _state.value = currentState.copy(
                        entries = currentState.entries + newEntries,
                        isPaginating = false,
                        endReached = !hasNext
                    )
                } catch (e: Exception) {
                    // 4. Handle errors (e.g., revert pagination state, show toast)
                    currentPage-- // Revert page count so they can try again
                    _state.value = currentState.copy(isPaginating = false)
                    _effect.emit(HomeEffect.ShowToast("Failed to load next page"))
                }
            }
        }
    }
}