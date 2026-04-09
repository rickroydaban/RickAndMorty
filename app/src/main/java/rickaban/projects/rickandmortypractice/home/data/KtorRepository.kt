package rickaban.projects.rickandmortypractice.home.data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import rickaban.projects.rickandmortypractice.home.domain.Entry
import rickaban.projects.rickandmortypractice.home.domain.Repository

private const val BASE_URL = "rickandmortyapi.com/api/"

class KtorRepository(
    private val httpClient: HttpClient
) : Repository {

    override suspend fun getEntries(
        page: Int
    ): Pair<List<Entry>, Boolean> {
        return withContext(Dispatchers.IO) {
            val response: EntryResponseDto = httpClient
                .get("https://${BASE_URL}character/?page=$page")
                .body()

            val entries = response.results.map { it.toDomain() }
            val hasNextPage = response.info.next != null

            Pair(entries, hasNextPage)
        }
    }

    override suspend fun getEntry(entryId: Long): Entry {
        return withContext(Dispatchers.IO) {
            val response: EntryDto = httpClient
                .get("https://${BASE_URL}character/$entryId")
                .body()

            response.toDomain()
        }
    }
}