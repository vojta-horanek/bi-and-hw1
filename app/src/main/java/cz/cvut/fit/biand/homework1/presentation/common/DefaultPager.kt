package cz.cvut.fit.biand.homework1.presentation.common

import androidx.paging.InvalidatingPagingSourceFactory
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource

/**
 * A class which helps to manage a paging source and it's pager.
 *
 * It allows to invalidate the paging source by keeping a reference to it.
 *
 * @param pagingSourceFactory A function which returns a [PagingSource] this pager should use.
 */
class DefaultPager<T : Any>(
    private val pagingSourceFactory: () -> PagingSource<String, T>,
) {
    private val invalidatingFactory = InvalidatingPagingSourceFactory {
        pagingSourceFactory()
    }

    /**
     * Invalidates the paging source inside [invalidatingFactory] and creates a new one using the
     * [pagingSourceFactory] from constructor. This can be viewed as a refresh of the data, possibly
     * with new parameters.
     */
    fun invalidate() {
        invalidatingFactory.invalidate()
    }

    /**
     * A [Pager] object fot this [DefaultPager], constructed with [invalidatingFactory]
     */
    private val pager: Pager<String, T> = getPager {
        invalidatingFactory()
    }

    /**
     * A shortcut to retrieve the flow from [pager]. This is commonly the only thing needed for presenting the data.
     */
    val flow get() = pager.flow

    companion object {
        /**
         * Returns the pager for a paging source.
         * @param pageSize Defines the number of items loaded at once from the [PagingSource]
         * @param prefetchDistance defines how far from the edge of loaded content an access must be to
         * trigger further loading. Typically should be set several times the number of visible items
         * onscreen.
         * @param pagingSourceFactory the function which returns an instance of a paging source
         * @return pager for a paging source
         */
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

        private const val DEFAULT_PAGE_SIZE = 50
        private const val DEFAULT_PREFETCH_DISTANCE = 20
    }
}
