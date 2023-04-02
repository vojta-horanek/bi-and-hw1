package cz.cvut.fit.biand.homework1.presentation.common

import androidx.paging.InvalidatingPagingSourceFactory
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource

class DefaultPager<T : Any>(
    private val pagingSourceFactory: () -> PagingSource<String, T>,
) {
    private val invalidatingFactory = InvalidatingPagingSourceFactory {
        pagingSourceFactory()
    }

    fun invalidate() {
        invalidatingFactory.invalidate()
    }

    private val pager: Pager<String, T> = getPager {
        invalidatingFactory()
    }

    val flow get() = pager.flow

    companion object {
        internal fun <T : Any> getPager(
            prefetchDistance: Int = DEFAULT_PREFETCH_DISTANCE,
            pagingSourceFactory: () -> PagingSource<String, T>,
        ) = Pager(
            config = PagingConfig(
                pageSize = 1,
                prefetchDistance = prefetchDistance,
                enablePlaceholders = false,
                initialLoadSize = 1,
            ),
            pagingSourceFactory = pagingSourceFactory,
        )

        private const val DEFAULT_PREFETCH_DISTANCE = 20
    }
}
