package cz.cvut.fit.biand.homework1.presentation.search

import androidx.lifecycle.viewModelScope
import cz.cvut.fit.biand.homework1.domain.model.Character
import cz.cvut.fit.biand.homework1.domain.usecase.GetCharactersUseCase
import cz.cvut.fit.biand.homework1.presentation.common.IntentViewModel
import cz.cvut.fit.biand.homework1.presentation.common.VmIntent
import cz.cvut.fit.biand.homework1.presentation.common.VmState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

internal class SearchViewModel(
    private val getCharacters: GetCharactersUseCase,
) : IntentViewModel<SearchViewModel.State, SearchViewModel.Intent>(State()) {

    private var searchJob: Job? = null

    override fun State.applyIntent(intent: Intent) = when (intent) {
        is OnCharactersLoaded -> copy(loading = false, items = intent.characters)
        is OnError -> copy(loading = false, error = intent.error)
        is Intent.OnQueryChanged -> onQueryChanged(intent.query)
        OnLoading -> copy(loading = true)
        Intent.OnClearClick -> onQueryChanged("")
    }

    private fun State.onQueryChanged(query: String): State {
        if (query.isEmpty()) {
            searchJob?.cancel()
            return copy(query = query, loading = false, items = persistentListOf())
        }

        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(SEARCH_DELAY)
            onIntent(OnLoading)
            loadCharacters(query)
        }
        return copy(query = query)
    }

    private suspend fun loadCharacters(query: String) {
        getCharacters(query)
            .onSuccess { characters ->
                onIntent(OnCharactersLoaded(characters.toPersistentList()))
            }
            .onFailure { error ->
                onIntent(OnError(error))
            }
    }

    sealed interface Intent : VmIntent {
        object OnClearClick : Intent

        data class OnQueryChanged(val query: String) : Intent
    }

    private data class OnCharactersLoaded(val characters: ImmutableList<Character>) : Intent
    private data class OnError(val error: Throwable) : Intent
    private object OnLoading : Intent

    data class State(
        val query: String = "",
        val loading: Boolean = true,
        val items: ImmutableList<Character> = persistentListOf(),
        val error: Throwable? = null,
    ) : VmState {
        val isLabelVisible = query.isEmpty()
        val isClearVisible = query.isNotEmpty()
        val isNoResultsVisible = query.isNotEmpty() && items.isEmpty() && !loading
    }

    private companion object {
        val SEARCH_DELAY = 300.milliseconds
    }
}