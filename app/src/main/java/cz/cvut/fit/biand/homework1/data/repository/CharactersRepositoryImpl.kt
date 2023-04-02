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
            localSource.addCharacters(wrapper.list)
            wrapper.map { it.copy(isFavourite = it.id in favourites) }
        }

    override suspend fun getCharacter(id: Long): Result<Character> {
        val localCharacter = localSource.getCharacterById(id)
        if (localCharacter != null) {
            return Result.success(localCharacter)
        }
        return remoteSource.getCharacter(id).map {
            val favourites = localSource.getFavouriteCharacters()
                .map { favourite -> favourite.id }
                .toSet()
            it.copy(isFavourite = it.id in favourites)
        }
    }

    override suspend fun getFavouriteCharacters(): Result<List<Character>> =
        Result.success(localSource.getFavouriteCharacters())

    override suspend fun addFavourite(id: Long) =
        localSource.addFavourite(id)

    override suspend fun removeFavourite(id: Long) =
        localSource.removeFavourite(id)
}