package cz.cvut.fit.biand.homework1.presentation.common

import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems

val <T : Any> LazyPagingItems<T>.isEmpty
    get(): Boolean {
        return loadState.refresh is LoadState.NotLoading &&
                loadState.append is LoadState.NotLoading && loadState.append.endOfPaginationReached &&
                loadState.prepend is LoadState.NotLoading && loadState.prepend.endOfPaginationReached &&
                itemCount == 0
    }