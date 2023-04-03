package cz.cvut.fit.biand.homework1.data.source

import cz.cvut.fit.biand.homework1.domain.model.Character
import kotlinx.coroutines.flow.Flow

internal interface CharactersLocalSource {

    suspend fun getCharacterById(id: Long): Flow<Character?>
    suspend fun getCharacters(): List<Character>

    suspend fun addCharacters(characters: List<Character>)
    suspend fun getFavouriteCharacters(): List<Character>

    suspend fun addFavourite(id: Long)

    suspend fun removeFavourite(id: Long)
}