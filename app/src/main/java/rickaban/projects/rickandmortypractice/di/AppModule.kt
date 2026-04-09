package rickaban.projects.rickandmortypractice.di

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import rickaban.projects.rickandmortypractice.home.data.KtorRepository
import rickaban.projects.rickandmortypractice.home.domain.Repository
import rickaban.projects.rickandmortypractice.home.presentation.detail.DetailViewModel
import rickaban.projects.rickandmortypractice.home.presentation.home.HomeViewModel

val appModule = module {

    single {
        HttpClient(CIO) { // CIO is Ktor's asynchronous coroutine-based engine
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true // Crucial for APIs that return fields we don't map
                    isLenient = true
                })
            }
        }
    }

    single<Repository> {
        KtorRepository(httpClient = get())
    }

    viewModel {
        HomeViewModel(repository = get())
    }
    viewModel { (entryId: Long) ->
        DetailViewModel(entryId = entryId, repository = get())
    }
}