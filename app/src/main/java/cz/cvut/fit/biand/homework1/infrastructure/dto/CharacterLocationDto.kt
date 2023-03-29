package cz.cvut.fit.biand.homework1.infrastructure.dto

import cz.cvut.fit.biand.homework1.domain.model.CharacterLocation
import kotlinx.serialization.Serializable

@Serializable
internal data class CharacterLocationDto(
    val name: String? = null,
    val url: String? = null,
)

internal fun CharacterLocationDto.toDomain() = CharacterLocation(
    name = name,
    url = url,
)