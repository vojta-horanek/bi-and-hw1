package cz.cvut.fit.biand.homework1.infrastructure.source

import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToOneOrNull
import cz.cvut.fit.biand.homework1.data.source.CharactersLocalSource
import cz.cvut.fit.biand.homework1.domain.model.Character
import cz.cvut.fit.biand.homework1.infrastructure.database.*
import cz.cvut.fit.biand.homework1.infrastructure.dto.CharacterLocationDto
import cz.cvut.fit.biand.homework1.infrastructure.dto.CharacterOriginDto
import cz.cvut.fit.biand.homework1.infrastructure.dto.toDomain
import cz.cvut.fit.biand.homework1.infrastructure.entity.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

internal class CharactersLocalSourceImpl(
    private val database: Database,
) : CharactersLocalSource {
    override suspend fun getCharacterById(id: Long): Flow<Character?> {
        return database.characterQueries
            .getCharacterById(id)
            .asFlow()
            .mapToOneOrNull()
            .map {
                it?.toDomain()
            }
    }

    override suspend fun getCharacters(): List<Character> {
        return database.characterQueries
            .getCharacters()
            .executeAsList()
            .map {
                it.toDomain()
            }
    }

    override suspend fun addCharacters(characters: List<Character>) {
        characters.forEach { character ->
            database.characterQueries.addCharacter(
                character.toEntity()
            )
        }
    }

    override suspend fun getFavouriteCharacters(): List<Character> {
        return database.characterQueries
            .getFavourites()
            .executeAsList()
            .map { entity ->
                entity.toDomain()
            }
    }


    override suspend fun addFavourite(id: Long) {
        database
            .favouriteQueries
            .addFavourite(
                Favourite(
                    favouriteId = id,
                ),
            )
    }

    override suspend fun removeFavourite(id: Long) {
        database
            .favouriteQueries
            .removeFavourite(
                favouriteId = id,
            )
    }

    private fun GetCharacterById.toDomain() = Character(
        id = id,
        name = name,
        status = status,
        species = species,
        type = type,
        gender = gender,
        origin = origin?.asOrigin(),
        location = location?.asLocation(),
        image = image,
        episodes = episodes?.asEpisodes() ?: emptyList(),
        url = url,
        created = created,
        isFavourite = favouriteId != null,
    )

    private fun GetCharacters.toDomain() = Character(
        id = id,
        name = name,
        status = status,
        species = species,
        type = type,
        gender = gender,
        origin = origin?.asOrigin(),
        location = location?.asLocation(),
        image = image,
        episodes = episodes?.asEpisodes() ?: emptyList(),
        url = url,
        created = created,
        isFavourite = favouriteId != null,
    )

    private fun GetFavourites.toDomain() = Character(
        id = id,
        name = name,
        status = status,
        species = species,
        type = type,
        gender = gender,
        origin = origin.asOrigin(),
        location = location.asLocation(),
        image = image,
        episodes = episodes.asEpisodes(),
        url = url,
        created = created,
        isFavourite = true,
    )

    private fun String?.asOrigin() =
        this?.let { Json.decodeFromString<CharacterOriginDto>(it).toDomain() }

    private fun String?.asLocation() =
        this?.let { Json.decodeFromString<CharacterLocationDto>(it).toDomain() }

    private fun String?.asEpisodes(): List<String> =
        this?.let { Json.decodeFromString(it) } ?: emptyList()
}