package cz.cvut.fit.biand.homework1.presentation.overview

import cz.cvut.fit.biand.homework1.presentation.common.*
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf

internal class OverviewViewModel :
    IntentViewModel<OverviewViewModel.State, OverviewViewModel.Intent>(State()) {
    private fun pagingSourceFactory(name: String?) =
        get<CharactersPagingSource> { parametersOf(name) }


    private val pager = DefaultPager { pagingSourceFactory(null) }
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