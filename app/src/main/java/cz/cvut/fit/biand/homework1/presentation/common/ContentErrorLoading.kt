package cz.cvut.fit.biand.homework1.presentation.common

import androidx.compose.animation.Crossfade
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ContentErrorLoading(
    contentState: ContentState,
    modifier: Modifier = Modifier,
    errorContent: @Composable () -> Unit,
    emptyContent: @Composable () -> Unit,
    loadingContent: @Composable () -> Unit = { Loading() },
    content: @Composable () -> Unit,
) {
    Crossfade(
        targetState = contentState,
        modifier = modifier,
    ) { state ->
        when (state) {
            ContentState.Content -> content()
            ContentState.Empty -> emptyContent()
            ContentState.Error -> errorContent()
            ContentState.Loading -> loadingContent()
        }
    }
}

sealed interface ContentState {
    object Error : ContentState
    object Loading : ContentState
    object Empty : ContentState
    object Content : ContentState

    companion object
}

fun ContentState.Companion.fromState(
    error: Throwable?,
    loading: Boolean,
    empty: Boolean,
) = when {
    error != null -> ContentState.Error
    loading -> ContentState.Loading
    empty -> ContentState.Empty
    else -> ContentState.Content
}