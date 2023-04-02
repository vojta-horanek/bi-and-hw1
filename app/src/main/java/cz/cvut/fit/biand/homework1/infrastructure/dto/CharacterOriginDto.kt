package cz.cvut.fit.biand.homework1.infrastructure.dto

import cz.cvut.fit.biand.homework1.domain.model.CharacterOrigin
import kotlinx.serialization.Serializable

@Serializable
internal data class CharacterOriginDto(
    val name: String? = null,
    val url: String? = null
)

internal fun CharacterOriginDto.toDomain() = CharacterOrigin(
    name = name,
    url = url,
)

internal fun CharacterOrigin.toDto() = CharacterOriginDto(
    name = name,
    url = url,
)