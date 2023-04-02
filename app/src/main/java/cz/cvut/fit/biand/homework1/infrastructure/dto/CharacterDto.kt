package cz.cvut.fit.biand.homework1.infrastructure.dto

import cz.cvut.fit.biand.homework1.domain.model.Character
import kotlinx.serialization.Serializable

@Serializable
internal data class CharacterDto(
    val id: Long,
    val name: String? = null,
    val status: String? = null,
    val species: String? = null,
    val type: String? = null,
    val gender: String? = null,
    val origin: CharacterOriginDto? = null,
    val location: CharacterLocationDto? = null,
    val image: String? = null,
    val episode: List<String> = emptyList(),
    val url: String? = null,
    val created: String? = null
)

internal fun CharacterDto.toDomain() = Character(
    id = id,
    name = name,
    status = status,
    species = species,
    type = type,
    gender = gender,
    origin = origin?.toDomain(),
    location = location?.toDomain(),
    image = image,
    episodes = episode,
    url = url,
    created = created,
    isFavourite = false,
)