package cz.cvut.fit.biand.homework1.presentation.overview

import cz.cvut.fit.biand.homework1.presentation.common.*
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf

internal class OverviewViewModel :
    IntentViewModel<OverviewViewModel.State, OverviewViewModel.Intent>(State()) {
    private fun pagingSourceFactory() =
        get<CharactersPagingSource> { parametersOf(null) }

    private val pager = DefaultPager { pagingSourceFactory() }
    val characters = pager.flow

    override fun State.applyIntent(intent: Intent) = when (intent) {
        Intent.OnRetryClick -> {
            pager.invalidate()
            this
        }
    }

    sealed interface Intent : VmIntent {
        object OnRetryClick : Intent
    }

    class State : VmState
}