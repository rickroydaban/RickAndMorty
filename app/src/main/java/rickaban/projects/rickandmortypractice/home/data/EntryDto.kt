package rickaban.projects.rickandmortypractice.home.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import rickaban.projects.rickandmortypractice.home.domain.Entry

@Serializable
data class EntryResponseDto(
    @SerialName("info") val info: InfoDto, // Added Info object,
    @SerialName("results") val results: List<EntryDto>
)

@Serializable
data class InfoDto(
    @SerialName("next") val next: String?, // If null, we are at the last page
    @SerialName("pages") val pages: Int
)

@Serializable
data class EntryDto(
    @SerialName("id") val id: Long,
    @SerialName("name") val name: String,
    @SerialName("image") val image: String,
    @SerialName("status") val status: String,
    @SerialName("species") val species: String,
    @SerialName("gender") val gender: String,
    @SerialName("origin") val origin: OriginDto,
    @SerialName("location") val location: LocationDto,
    @SerialName("episode") val episode: List<String>
)

@Serializable
data class OriginDto(
    @SerialName("name") val name: String
)

@Serializable
data class LocationDto(
    @SerialName("name") val name: String
)


// Mapper extension
fun EntryDto.toDomain(): Entry {
    return Entry(
        entryId = this.id,
        name = this.name,
        imageUrl = this.image,
        status = this.status,
        species = this.species,
        gender = this.gender,
        originName = this.origin.name,
        locationName = this.location.name,
        episodeCount = this.episode.size
    )
}