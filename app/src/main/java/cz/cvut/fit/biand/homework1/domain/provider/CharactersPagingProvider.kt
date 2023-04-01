package cz.cvut.fit.biand.homework1.domain.provider

import cz.cvut.fit.biand.homework1.domain.common.BasePagingProvider
import cz.cvut.fit.biand.homework1.domain.model.Character
import cz.cvut.fit.biand.homework1.domain.model.PagingWrapper
import cz.cvut.fit.biand.homework1.domain.usecase.GetCharactersUseCase

class CharactersPagingProvider(
    val name: String?,
    private val getCharactersUseCase: GetCharactersUseCase,
) : BasePagingProvider<Character> {
    override suspend fun fetchData(page: String): Result<PagingWrapper<Character>> {
        return getCharactersUseCase(
            GetCharactersUseCase.Params(
                name = name,
                page = page,
            )
        )
    }
}