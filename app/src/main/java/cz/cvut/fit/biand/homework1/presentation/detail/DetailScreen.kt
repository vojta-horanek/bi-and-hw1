package cz.cvut.fit.biand.homework1.presentation.detail

import android.annotation.SuppressLint
import androidx.compose.animation.Crossfade
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import cz.cvut.fit.biand.homework1.R
import cz.cvut.fit.biand.homework1.presentation.common.*
import cz.cvut.fit.biand.homework1.presentation.navigation.Routes
import cz.cvut.fit.biand.homework1.presentation.navigation.composableDestination
import cz.cvut.fit.biand.homework1.presentation.theme.IconSize
import cz.cvut.fit.biand.homework1.presentation.theme.Space
import org.koin.androidx.compose.koinViewModel

fun NavController.navigateToDetail(id: Long) {
    navigate(Routes.Detail(id))
}

fun NavGraphBuilder.detailRoute(
    onBackPressed: () -> Unit,
) {
    composableDestination(
        destination = Routes.Detail
    ) {
        val args = Routes.Detail.Args(it.arguments)
        DetailRoute(
            id = args.id,
            onBackPressed = onBackPressed,
        )
    }
}

@Composable
internal fun DetailRoute(
    id: Long,
    onBackPressed: () -> Unit,
    viewModel: DetailViewModel = koinViewModel(),
) {
    val state by viewModel.collectState()

    LaunchedEffect(id) {
        viewModel.onIntent(DetailViewModel.Intent.OnViewInitialized(id))
    }

    DetailScreen(
        state = state,
        onBackPressed = onBackPressed,
        onFavouriteClick = {
            viewModel.onIntent(DetailViewModel.Intent.OnFavouriteClick)
        },
        onRetryClick = {
            viewModel.onIntent(DetailViewModel.Intent.OnRetryClick)
        }
    )
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
private fun DetailScreen(
    state: DetailViewModel.State,
    onFavouriteClick: () -> Unit,
    onRetryClick: () -> Unit,
    onBackPressed: () -> Unit,
) {
    Scaffold(
        topBar = {
            AppToolbar(
                navigationIcon = {
                    IconButton(onClick = onBackPressed) {
                        Icon(
                            painter = painterResource(R.drawable.ic_arrow_left),
                            contentDescription = null,
                            modifier = Modifier.size(IconSize.Medium)
                        )
                    }
                },
                title = {
                    Crossfade(state.character?.name.orEmpty()) { name ->
                        Text(
                            text = name,
                        )
                    }
                },
                actions = {
                    androidx.compose.animation.AnimatedVisibility(
                        visible = !state.loading,
                        enter = fadeIn(),
                        exit = fadeOut(),
                    ) {
                        IconButton(
                            onClick = onFavouriteClick,
                        ) {
                            Crossfade(
                                targetState = state.isFavourite
                            ) { isFavourite ->
                                val painter = if (isFavourite) {
                                    painterResource(R.drawable.ic_favourite_selected)
                                } else {
                                    painterResource(R.drawable.ic_favourite)
                                }

                                val tint = if (isFavourite) {
                                    MaterialTheme.colors.secondary
                                } else {
                                    MaterialTheme.colors.onPrimary
                                }

                                Icon(
                                    painter = painter,
                                    tint = tint,
                                    contentDescription = null,
                                    modifier = Modifier.size(IconSize.Medium)
                                )
                            }
                        }
                    }
                },
            )
        }
    ) {
        ContentErrorLoading(
            contentState = ContentState.fromState(
                error = state.error,
                empty = false,
                loading = state.loading,
            ),
            errorContent = {
                Error(
                    onRetryClick = onRetryClick,
                )
            },
            emptyContent = { },
        ) {
            if (state.character != null) {
                CharacterDetail(
                    character = state.character,
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .padding(Space.Medium)
                )
            }
        }
    }
}
