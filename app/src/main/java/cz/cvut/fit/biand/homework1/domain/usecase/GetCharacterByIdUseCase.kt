package cz.cvut.fit.biand.homework1.domain.usecase

import cz.cvut.fit.biand.homework1.domain.common.UseCase
import cz.cvut.fit.biand.homework1.domain.model.Character
import cz.cvut.fit.biand.homework1.domain.repository.CharactersRepository

class GetCharacterByIdUseCase internal constructor(
    private val charactersRepository: CharactersRepository,
) : UseCase<Long, Result<Character>>() {
    override suspend fun doWork(params: Long): Result<Character> =
        charactersRepository.getCharacter(params)
}