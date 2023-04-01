package cz.cvut.fit.biand.homework1.presentation.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import cz.cvut.fit.biand.homework1.domain.common.BasePagingProvider

/**
 * A base implementation for a paging source for paging v3.
 * When implementing, only the [provider] must be provided, everything else is handled by this class.
 */
abstract class BasePagingSource<T : Any>(
    private val provider: BasePagingProvider<T>,
    private val idProvider: (T) -> String,
) : PagingSource<String, T>() {

    override fun getRefreshKey(state: PagingState<String, T>): String? {
        return state.anchorPosition?.let { state.closestItemToPosition(it)?.let(idProvider) }
    }

    override suspend fun load(params: LoadParams<String>): LoadResult<String, T> {
        return provider.fetchData(params.key ?: "1")
            .fold(
                onSuccess = { response ->
                    LoadResult.Page(
                        data = response.list,
                        nextKey = response.pagination.nextKey,
                        prevKey = response.pagination.prevKey,
                    )
                },
                onFailure = { throwable ->
                    LoadResult.Error(throwable)
                }
            )
    }
}
