package cz.cvut.fit.biand.homework1.data.source

import cz.cvut.fit.biand.homework1.domain.model.Character

internal interface CharactersLocalSource {
    suspend fun getFavouriteCharacters(): Set<Character>

    suspend fun addFavourite(character: Character)

    suspend fun removeFavourite(character: Character)
}