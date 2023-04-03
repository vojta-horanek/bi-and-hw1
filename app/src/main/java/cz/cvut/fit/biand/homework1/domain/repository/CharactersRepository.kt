package cz.cvut.fit.biand.homework1.domain.repository

import cz.cvut.fit.biand.homework1.domain.model.Character
import cz.cvut.fit.biand.homework1.domain.model.PagingWrapper
import kotlinx.coroutines.flow.Flow

internal interface CharactersRepository {
    suspend fun getCharacters(
        page: String,
        name: String? = null,
    ): Result<PagingWrapper<Character>>

    suspend fun getCharacter(
        id: Long,
    ): Flow<Result<Character>>

    suspend fun getFavouriteCharacters(): Result<List<Character>>

    suspend fun addFavourite(id: Long)

    suspend fun removeFavourite(id: Long)
}