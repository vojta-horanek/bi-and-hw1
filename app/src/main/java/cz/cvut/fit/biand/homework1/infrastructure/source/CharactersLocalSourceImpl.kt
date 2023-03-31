package cz.cvut.fit.biand.homework1.infrastructure.source

import cz.cvut.fit.biand.homework1.data.source.CharactersLocalSource
import cz.cvut.fit.biand.homework1.domain.model.Character

internal class CharactersLocalSourceImpl : CharactersLocalSource {
    private val favourites = mutableSetOf<Character>()
    override suspend fun getFavouriteCharacters(): Set<Character> {
        return favourites
    }

    override suspend fun addFavourite(character: Character) {
        favourites.add(character)
    }

    override suspend fun removeFavourite(character: Character) {
        favourites.remove(character)
    }
}