package cz.cvut.fit.biand.homework1.data.repository

import cz.cvut.fit.biand.homework1.data.source.CharactersLocalSource
import cz.cvut.fit.biand.homework1.data.source.CharactersRemoteSource
import cz.cvut.fit.biand.homework1.domain.model.Character
import cz.cvut.fit.biand.homework1.domain.model.map
import cz.cvut.fit.biand.homework1.domain.repository.CharactersRepository

internal class CharactersRepositoryImpl(
    private val remoteSource: CharactersRemoteSource,
    private val localSource: CharactersLocalSource,
) : CharactersRepository {
    override suspend fun getCharacters(page: String, name: String?) =
        remoteSource.getCharacters(page, name).map { wrapper ->
            val favourites = localSource.getFavouriteCharacters()
                .map { it.id }
                .toSet()
            wrapper.map { it.copy(isFavourite = it.id in favourites) }
        }

    override suspend fun getCharacter(id: Long) = remoteSource.getCharacter(id).map {
        val favourites = localSource.getFavouriteCharacters()
            .map { favourite -> favourite.id }
            .toSet()
        it.copy(isFavourite = it.id in favourites)
    }

    override suspend fun getFavouriteCharacters(): Result<List<Character>> =
        Result.success(localSource.getFavouriteCharacters().toList())

    override suspend fun addFavourite(character: Character) =
        localSource.addFavourite(character)

    override suspend fun removeFavourite(character: Character) =
        localSource.removeFavourite(character)
}