package cz.cvut.fit.biand.homework1.presentation.common

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems


@Composable
inline fun <T : Any> LazyPagingItemsScreen(
    lazyPagingItems: LazyPagingItems<T>,
    crossinline emptyContent: @Composable (
        contentPadding: PaddingValues,
    ) -> Unit,
    crossinline errorContent: @Composable (
        contentPadding: PaddingValues,
    ) -> Unit,
    crossinline loadingContent: @Composable (
        contentPadding: PaddingValues,
    ) -> Unit,
    crossinline appendErrorItem: LazyListScope.() -> Unit,
    crossinline appendLoadingItem: LazyListScope.() -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(),
    crossinline content: @Composable (
        contentPadding: PaddingValues,
        appendContent: LazyListScope.() -> Unit,
    ) -> Unit,
) {
    Crossfade(
        targetState = lazyPagingItems.isEmpty,
        modifier = modifier,
    ) { isEmpty ->
        if (isEmpty) {
            emptyContent(contentPadding)
        } else {
            Crossfade(
                targetState = lazyPagingItems.loadState.refresh,
            ) { refreshState ->
                when (refreshState) {
                    is LoadState.NotLoading -> {
                        content(contentPadding) {
                            when (lazyPagingItems.loadState.append) {
                                is LoadState.Error -> {
                                    appendErrorItem()
                                }
                                is LoadState.Loading -> {
                                    appendLoadingItem()
                                }
                                is LoadState.NotLoading -> {}
                            }
                        }
                    }
                    is LoadState.Loading -> {
                        loadingContent(contentPadding)
                    }
                    is LoadState.Error -> {
                        errorContent(contentPadding)
                    }
                }
            }
        }
    }
}