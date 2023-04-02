package cz.cvut.fit.biand.homework1.infrastructure.source

import cz.cvut.fit.biand.homework1.data.source.CharactersLocalSource
import cz.cvut.fit.biand.homework1.domain.model.Character
import cz.cvut.fit.biand.homework1.infrastructure.database.Database
import cz.cvut.fit.biand.homework1.infrastructure.entity.toDomain
import cz.cvut.fit.biand.homework1.infrastructure.entity.toEntity

internal class CharactersLocalSourceImpl(
    private val database: Database,
) : CharactersLocalSource {
    override suspend fun getCharacterById(id: Long): Character? {
        return database.characterQueries
            .getCharacterById(id)
            .executeAsOneOrNull()
            ?.toDomain()
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
            .characterQueries
            .setFavourite(
                favourite = true,
                id = id,
            )
    }

    override suspend fun removeFavourite(id: Long) {
        database
            .characterQueries
            .setFavourite(
                favourite = false,
                id = id,
            )
    }
}