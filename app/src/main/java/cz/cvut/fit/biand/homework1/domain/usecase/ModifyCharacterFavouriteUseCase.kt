package cz.cvut.fit.biand.homework1.domain.usecase

import cz.cvut.fit.biand.homework1.domain.common.UseCase
import cz.cvut.fit.biand.homework1.domain.model.Character
import cz.cvut.fit.biand.homework1.domain.repository.CharactersRepository

class ModifyCharacterFavouriteUseCase internal constructor(
    private val charactersRepository: CharactersRepository,
) : UseCase<ModifyCharacterFavouriteUseCase.Params, Unit>() {
    override suspend fun doWork(params: Params) {
        if (params.isFavourite) {
            charactersRepository.addFavourite(params.character.id)
        } else {
            charactersRepository.removeFavourite(params.character.id)
        }
    }

    data class Params(
        val character: Character,
        val isFavourite: Boolean,
    )
}