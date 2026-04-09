package rickaban.projects.rickandmortypractice.home.presentation.detail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun DetailHost(
    entryId: Long,
    onNavigateBack: () -> Unit
) {
    val viewModel: DetailViewModel = koinViewModel(
        key = entryId.toString(),
        parameters = { parametersOf(entryId) }
    )

    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is DetailEffect.NavigateBack -> onNavigateBack()
                is DetailEffect.ShowToast -> { /* Show toast */ }
            }
        }
    }

    DetailScreen(
        state = state,
        onIntent = viewModel::processIntent
    )
}