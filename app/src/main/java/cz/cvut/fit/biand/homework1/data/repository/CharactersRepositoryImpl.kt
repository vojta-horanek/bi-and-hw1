package cz.cvut.fit.biand.homework1.data.repository

import cz.cvut.fit.biand.homework1.data.source.CharactersLocalSource
import cz.cvut.fit.biand.homework1.data.source.CharactersRemoteSource
import cz.cvut.fit.biand.homework1.domain.model.Character
import cz.cvut.fit.biand.homework1.domain.model.map
import cz.cvut.fit.biand.homework1.domain.repository.CharactersRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*

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

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun getCharacter(id: Long): Flow<Result<Character>> = flow {
        localSource
            .getCharacterById(id)
            .flatMapLatest { character ->
                flow {
                    if (character == null) {
                        val remote = getCharacterFromRemote(id)
                            .onSuccess {
                                localSource.addCharacters(listOf(it))
                            }
                        emit(remote)
                    } else {
                        emit(Result.success(character))
                    }
                }
            }
            .collect(this)
    }

    private suspend fun getCharacterFromRemote(id: Long) = remoteSource.getCharacter(id).map {
        val favourites = localSource.getFavouriteCharacters()
            .map { favourite -> favourite.id }
            .toSet()
        it.copy(isFavourite = it.id in favourites)
    }


    override suspend fun getFavouriteCharacters(): Result<List<Character>> =
        Result.success(localSource.getFavouriteCharacters())

    override suspend fun addFavourite(id: Long) =
        localSource.addFavourite(id)

    override suspend fun removeFavourite(id: Long) =
        localSource.removeFavourite(id)
}