package cz.cvut.fit.biand.homework1.presentation.common

import kotlinx.coroutines.flow.StateFlow

interface StateReducerFlow<State, Intent> : StateFlow<State> {
    fun handleIntent(intent: Intent)
}