package cz.cvut.fit.biand.homework1.presentation.detail

import androidx.lifecycle.viewModelScope
import cz.cvut.fit.biand.homework1.domain.model.Character
import cz.cvut.fit.biand.homework1.domain.usecase.GetCharacterByIdUseCase
import cz.cvut.fit.biand.homework1.domain.usecase.ModifyCharacterFavouriteUseCase
import cz.cvut.fit.biand.homework1.presentation.common.IntentViewModel
import cz.cvut.fit.biand.homework1.presentation.common.VmIntent
import cz.cvut.fit.biand.homework1.presentation.common.VmState
import cz.cvut.fit.biand.homework1.presentation.common.intentFlow
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

internal class DetailViewModel(
    private val getCharacter: GetCharacterByIdUseCase,
    private val modifyCharacterFavourite: ModifyCharacterFavouriteUseCase,
) : IntentViewModel<DetailViewModel.State, DetailViewModel.Intent>(State()) {

    private var characterJob: Job? = null

    override fun State.applyIntent(intent: Intent) = when (intent) {
        is OnCharacterLoaded -> copy(loading = false, character = intent.character, error = null)
        is OnError -> copy(loading = false, error = intent.error)
        is Intent.OnViewInitialized -> {
            if (character == null) {
                loadCharacter(intent.id)
                copy(loading = true, id = intent.id)
            } else {
                copy(error = null)
            }
        }
        Intent.OnFavouriteClick -> {
            character?.let { modifyFavourite(it, !isFavourite) }
            copy(character = character?.copy(isFavourite = !isFavourite))
        }
        Intent.OnRetryClick -> {
            if (id != null) {
                loadCharacter(id)
            }
            copy(loading = true, error = null)
        }
    }

    private fun modifyFavourite(character: Character, isFavourite: Boolean) =
        viewModelScope.launch {
            modifyCharacterFavourite(
                ModifyCharacterFavouriteUseCase.Params(
                    character = character,
                    isFavourite = isFavourite,
                )
            )
        }

    private fun loadCharacter(id: Long) {
        characterJob?.cancel()
        characterJob = intentFlow(
            producer = {
                getCharacter(id)
            },
            intent = { result ->
                result
                    .fold(
                        onSuccess = {
                            OnCharacterLoaded(it)
                        },
                        onFailure = {
                            OnError(it)
                        }
                    )
            }
        )
    }

    sealed interface Intent : VmIntent {
        data class OnViewInitialized(val id: Long) : Intent
        object OnFavouriteClick : Intent
        object OnRetryClick : Intent
    }

    private data class OnCharacterLoaded(val character: Character) : Intent
    private data class OnError(val error: Throwable) : Intent

    data class State(
        val id: Long? = null,
        val loading: Boolean = false, // So fast showing a loading actually makes the UX worse
        val character: Character? = null,
        val error: Throwable? = null,
    ) : VmState {
        val isFavourite = character?.isFavourite == true
    }
}