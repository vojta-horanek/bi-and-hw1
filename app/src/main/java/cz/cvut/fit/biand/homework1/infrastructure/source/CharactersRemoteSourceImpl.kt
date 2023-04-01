package cz.cvut.fit.biand.homework1.infrastructure.source

import cz.cvut.fit.biand.homework1.data.source.CharactersRemoteSource
import cz.cvut.fit.biand.homework1.infrastructure.api.CharactersApi
import cz.cvut.fit.biand.homework1.infrastructure.dto.toDomain

internal class CharactersRemoteSourceImpl(
    private val api: CharactersApi,
) : CharactersRemoteSource {
    override suspend fun getCharacters(page: String, name: String?) =
        api.getCharacters(page, name)
            .map { paging -> paging.toDomain { dto -> dto.toDomain() } }

    override suspend fun getCharacter(id: Long) =
        api.getCharacter(id).map { dto -> dto.toDomain() }
}