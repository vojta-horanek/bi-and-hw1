package cz.cvut.fit.biand.homework1.domain.usecase

import cz.cvut.fit.biand.homework1.domain.common.UseCase
import cz.cvut.fit.biand.homework1.domain.model.Character
import cz.cvut.fit.biand.homework1.domain.repository.CharactersRepository
import kotlinx.coroutines.flow.Flow

class GetCharacterByIdUseCase internal constructor(
    private val charactersRepository: CharactersRepository,
) : UseCase<Long, Flow<Result<Character>>>() {
    override suspend fun doWork(params: Long): Flow<Result<Character>> =
        charactersRepository.getCharacter(params)
}