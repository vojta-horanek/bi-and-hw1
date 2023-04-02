package cz.cvut.fit.biand.homework1.infrastructure.entity

import cz.cvut.fit.biand.homework1.domain.model.Character
import cz.cvut.fit.biand.homework1.infrastructure.database.CharacterEntity
import cz.cvut.fit.biand.homework1.infrastructure.dto.CharacterLocationDto
import cz.cvut.fit.biand.homework1.infrastructure.dto.CharacterOriginDto
import cz.cvut.fit.biand.homework1.infrastructure.dto.toDomain
import cz.cvut.fit.biand.homework1.infrastructure.dto.toDto
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

internal fun CharacterEntity.toDomain() = Character(
    id = id,
    name = name,
    status = status,
    species = species,
    type = type,
    gender = gender,
    origin = origin?.let { Json.decodeFromString<CharacterOriginDto>(it).toDomain() },
    location = location?.let { Json.decodeFromString<CharacterLocationDto>(it).toDomain() },
    image = image,
    episodes = episodes?.let { Json.decodeFromString(it) } ?: emptyList(),
    url = url,
    created = created,
    isFavourite = favourite,
)

internal fun Character.toEntity() = CharacterEntity(
    id = id,
    name = name,
    status = status,
    species = species,
    type = type,
    gender = gender,
    origin = origin?.let { Json.encodeToString(it.toDto()) },
    location = location?.let { Json.encodeToString(it.toDto()) },
    image = image,
    episodes = episodes.let { Json.encodeToString(it) },
    url = url,
    created = created,
    favourite = isFavourite,
)