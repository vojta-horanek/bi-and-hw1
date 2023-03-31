package cz.cvut.fit.biand.homework1.presentation.overview

import androidx.lifecycle.viewModelScope
import cz.cvut.fit.biand.homework1.domain.model.Character
import cz.cvut.fit.biand.homework1.domain.usecase.GetCharactersUseCase
import cz.cvut.fit.biand.homework1.presentation.common.IntentViewModel
import cz.cvut.fit.biand.homework1.presentation.common.VmIntent
import cz.cvut.fit.biand.homework1.presentation.common.VmState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.launch

internal class OverviewViewModel(
    private val getCharacters: GetCharactersUseCase,
) : IntentViewModel<OverviewViewModel.State, OverviewViewModel.Intent>(State()) {

    override fun State.applyIntent(intent: Intent) = when (intent) {
        is OnCharactersLoaded -> copy(loading = false, items = intent.characters)
        is OnError -> copy(loading = false, error = intent.error)
        is Intent.OnViewInitialized -> {
            loadCharacters()
            copy(loading = true)
        }
        Intent.OnRetryClick -> {
            loadCharacters()
            copy(loading = true)
        }
    }

    private fun loadCharacters() = viewModelScope.launch {
        getCharacters(null)
            .onSuccess { characters ->
                onIntent(OnCharactersLoaded(characters.toPersistentList()))
            }
            .onFailure { error ->
                onIntent(OnError(error))
            }
    }

    sealed interface Intent : VmIntent {
        object OnViewInitialized : Intent
        object OnRetryClick : Intent
    }

    private data class OnCharactersLoaded(val characters: ImmutableList<Character>) : Intent
    private data class OnError(val error: Throwable) : Intent

    data class State(
        val loading: Boolean = true,
        val items: ImmutableList<Character> = persistentListOf(),
        val error: Throwable? = null,
    ) : VmState {
        val isEmpty = items.isEmpty()
    }
}