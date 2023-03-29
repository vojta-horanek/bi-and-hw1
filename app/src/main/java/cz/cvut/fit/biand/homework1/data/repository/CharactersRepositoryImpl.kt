package cz.cvut.fit.biand.homework1.data.repository

import cz.cvut.fit.biand.homework1.data.source.CharactersRemoteSource
import cz.cvut.fit.biand.homework1.domain.repository.CharactersRepository

internal class CharactersRepositoryImpl(
    private val remoteSource: CharactersRemoteSource,
) : CharactersRepository {
    override suspend fun getCharacters(name: String?) = remoteSource.getCharacters(name)
    override suspend fun getCharacter(id: Long) = remoteSource.getCharacter(id)
}