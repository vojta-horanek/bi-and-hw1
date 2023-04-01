package cz.cvut.fit.biand.homework1.data.source

import cz.cvut.fit.biand.homework1.domain.model.Character
import cz.cvut.fit.biand.homework1.domain.model.PagingWrapper

internal interface CharactersRemoteSource {
    suspend fun getCharacters(
        page: String,
        name: String? = null,
    ): Result<PagingWrapper<Character>>

    suspend fun getCharacter(
        id: Long,
    ): Result<Character>
}