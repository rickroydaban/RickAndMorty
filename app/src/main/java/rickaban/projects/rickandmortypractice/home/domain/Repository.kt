package rickaban.projects.rickandmortypractice.home.domain

interface Repository {
    suspend fun getEntries(page: Int): Pair<List<Entry>, Boolean>
    suspend fun getEntry(entryId: Long): Entry
}