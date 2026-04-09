package rickaban.projects.rickandmortypractice.home.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import rickaban.projects.rickandmortypractice.home.domain.Repository

class DetailViewModel(
    private val entryId: Long,
    private val repository: Repository
) : ViewModel() {

    private val _state = MutableStateFlow<DetailState>(DetailState.Loading)
    val state: StateFlow<DetailState> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<DetailEffect>()
    val effect: SharedFlow<DetailEffect> = _effect.asSharedFlow()

    init {
        fetchEntry()
    }

    fun processIntent(intent: DetailIntent) {
        when (intent) {
            is DetailIntent.RetryClicked -> fetchEntry()
            is DetailIntent.BackClicked -> {
                viewModelScope.launch {
                    _effect.emit(DetailEffect.NavigateBack)
                }
            }
        }
    }

    private fun fetchEntry() {
        viewModelScope.launch {
            _state.value = DetailState.Loading
            try {
                val entry = repository.getEntry(entryId)
                _state.value = DetailState.Success(entry)
            } catch (e: Exception) {
                _state.value = DetailState.Error(e.localizedMessage ?: "Failed to load entry")
            }
        }
    }
}