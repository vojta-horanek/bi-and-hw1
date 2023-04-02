package cz.cvut.fit.biand.homework1.data.source

import cz.cvut.fit.biand.homework1.domain.model.Character

internal interface CharactersLocalSource {

    suspend fun getCharacterById(id: Long): Character?
    suspend fun getCharacters(): List<Character>

    suspend fun addCharacters(characters: List<Character>)
    suspend fun getFavouriteCharacters(): List<Character>

    suspend fun addFavourite(id: Long)

    suspend fun removeFavourite(id: Long)
}