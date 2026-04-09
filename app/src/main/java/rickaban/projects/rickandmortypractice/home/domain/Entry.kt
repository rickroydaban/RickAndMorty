package rickaban.projects.rickandmortypractice.home.domain

data class Entry(
    val entryId: Long,
    val name: String,
    val imageUrl: String,
    val status: String,
    val species: String,
    val gender: String,
    val originName: String,
    val locationName: String,
    val episodeCount: Int
)