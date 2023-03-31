package cz.cvut.fit.biand.homework1.infrastructure.api

import cz.cvut.fit.biand.homework1.infrastructure.dto.PagingDtoWrapper
import cz.cvut.fit.biand.homework1.infrastructure.dto.CharacterDto
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

internal interface CharactersApi {
    suspend fun getCharacters(
        name: String? = null,
    ): Result<PagingDtoWrapper<CharacterDto>>

    suspend fun getCharacter(
        id: Long,
    ): Result<CharacterDto>
}

internal class CharactersApiImpl(
    private val httpClient: HttpClient
) : CharactersApi {
    override suspend fun getCharacters(
        name: String?
    ): Result<PagingDtoWrapper<CharacterDto>> = catchingNetwork(
        defaultValue = PagingDtoWrapper()
    ) {
        httpClient
            .get("character") {
                parameter("name", name)
            }
            .body()
    }

    override suspend fun getCharacter(
        id: Long
    ): Result<CharacterDto> = catchingNetwork {
        httpClient
            .get("character/$id")
            .body()
    }
}
