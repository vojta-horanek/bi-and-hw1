package cz.cvut.fit.biand.homework1.domain.usecase

import cz.cvut.fit.biand.homework1.domain.common.UseCase
import cz.cvut.fit.biand.homework1.domain.model.Character
import cz.cvut.fit.biand.homework1.domain.model.PagingWrapper
import cz.cvut.fit.biand.homework1.domain.repository.CharactersRepository

class GetCharactersUseCase internal constructor(
    private val charactersRepository: CharactersRepository,
) : UseCase<GetCharactersUseCase.Params, Result<PagingWrapper<Character>>>() {
    override suspend fun doWork(params: Params): Result<PagingWrapper<Character>> =
        charactersRepository.getCharacters(
            page = params.page,
            name = params.name,
        )

    data class Params(
        val name: String?,
        val page: String,
    )
}