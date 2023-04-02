package cz.cvut.fit.biand.homework1.presentation.search

import androidx.lifecycle.viewModelScope
import cz.cvut.fit.biand.homework1.presentation.common.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf
import kotlin.time.Duration.Companion.milliseconds

internal class SearchViewModel :
    IntentViewModel<SearchViewModel.State, SearchViewModel.Intent>(State()) {

    private var query = ""
    private var searchJob: Job? = null
    private val pager = DefaultPager { pagingSourceFactory() }
    val characters = pager.flow

    override fun State.applyIntent(intent: Intent) = when (intent) {
        is Intent.OnQueryChanged -> onQueryChanged(intent.query)
        Intent.OnClearClick -> onQueryChanged("")
        Intent.OnRetryClick -> onQueryChanged(query)
    }

    private fun pagingSourceFactory() =
        get<CharactersPagingSource> { parametersOf(query) }

    private fun State.onQueryChanged(query: String): State {
        searchJob?.cancel()
        this@SearchViewModel.query = query
        if (query.isNotEmpty()) {
            searchJob = viewModelScope.launch {
                delay(SEARCH_DELAY)
                pager.invalidate()
            }
        }
        return copy(query = query)
    }

    sealed interface Intent : VmIntent {
        object OnClearClick : Intent
        object OnRetryClick : Intent

        data class OnQueryChanged(val query: String) : Intent
    }

    data class State(
        val query: String = "",
    ) : VmState {
        val isSearchLabelVisible = query.isEmpty()
        val isClearVisible = query.isNotEmpty()
        val isContentVisible = query.isNotEmpty()
    }

    private companion object {
        val SEARCH_DELAY = 300.milliseconds
    }
}