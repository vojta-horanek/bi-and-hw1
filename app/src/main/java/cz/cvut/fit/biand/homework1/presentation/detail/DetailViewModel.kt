package cz.cvut.fit.biand.homework1.presentation.detail

import androidx.lifecycle.viewModelScope
import cz.cvut.fit.biand.homework1.domain.model.Character
import cz.cvut.fit.biand.homework1.domain.usecase.GetCharacterByIdUseCase
import cz.cvut.fit.biand.homework1.presentation.common.IntentViewModel
import cz.cvut.fit.biand.homework1.presentation.common.VmIntent
import cz.cvut.fit.biand.homework1.presentation.common.VmState
import kotlinx.coroutines.launch

internal class DetailViewModel(
    private val getCharacter: GetCharacterByIdUseCase,
) : IntentViewModel<DetailViewModel.State, DetailViewModel.Intent>(State()) {

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
        Intent.OnFavouriteClick -> copy(character = character?.copy(isFavourite = !isFavourite))
        Intent.OnRetryClick -> {
            if (id != null) {
                loadCharacter(id)
            }
            copy(loading = true, error = null)
        }
    }

    private fun loadCharacter(id: Long) = viewModelScope.launch {
        getCharacter(id)
            .onSuccess { character ->
                onIntent(OnCharacterLoaded(character))
            }
            .onFailure { error ->
                onIntent(OnError(error))
            }
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
        val loading: Boolean = true,
        val character: Character? = null,
        val error: Throwable? = null,
    ) : VmState {
        val isFavourite = character?.isFavourite == true
    }
}