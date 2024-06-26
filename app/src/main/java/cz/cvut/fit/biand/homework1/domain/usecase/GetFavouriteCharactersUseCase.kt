package cz.cvut.fit.biand.homework1.domain.usecase

import cz.cvut.fit.biand.homework1.domain.common.UseCase
import cz.cvut.fit.biand.homework1.domain.model.Character
import cz.cvut.fit.biand.homework1.domain.repository.CharactersRepository

class GetFavouriteCharactersUseCase internal constructor(
    private val charactersRepository: CharactersRepository,
) : UseCase<String?, Result<List<Character>>>() {
    override suspend fun doWork(params: String?): Result<List<Character>> =
        charactersRepository.getFavouriteCharacters()
}