package cz.cvut.fit.biand.homework1.domain.common

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent

abstract class UseCase<in Params, out T>(
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : KoinComponent {
    protected abstract suspend fun doWork(params: Params): T
    suspend operator fun invoke(params: Params) = withContext(dispatcher) { doWork(params) }
}