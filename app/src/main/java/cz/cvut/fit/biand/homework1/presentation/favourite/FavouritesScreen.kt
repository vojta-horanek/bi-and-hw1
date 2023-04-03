package cz.cvut.fit.biand.homework1.presentation.favourite

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import cz.cvut.fit.biand.homework1.R
import cz.cvut.fit.biand.homework1.presentation.common.*
import cz.cvut.fit.biand.homework1.presentation.navigation.Routes
import cz.cvut.fit.biand.homework1.presentation.navigation.composableDestination
import cz.cvut.fit.biand.homework1.presentation.navigation.navigateToBottomNavigationItem
import cz.cvut.fit.biand.homework1.presentation.theme.Space
import org.koin.androidx.compose.koinViewModel

fun NavController.navigateToFavourites() {
    navigateToBottomNavigationItem(Routes.Favourites())
}

fun NavGraphBuilder.favouritesRoute(
    onNavigateToDetail: (id: Long) -> Unit,
) {
    composableDestination(
        destination = Routes.Favourites
    ) {
        FavouritesRoute(
            onNavigateToDetail = onNavigateToDetail,
        )
    }
}

@Composable
internal fun FavouritesRoute(
    onNavigateToDetail: (id: Long) -> Unit,
    viewModel: FavouritesViewModel = koinViewModel(),
) {
    val state by viewModel.collectState()

    LaunchedEffect(Unit) {
        viewModel.onIntent(FavouritesViewModel.Intent.OnViewInitialized)
    }

    FavouritesScreen(
        onCharacterClick = onNavigateToDetail,
        state = state,
        onRetryClick = {
            viewModel.onIntent(FavouritesViewModel.Intent.OnRetryClick)
        },
    )
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
private fun FavouritesScreen(
    state: FavouritesViewModel.State,
    onRetryClick: () -> Unit,
    onCharacterClick: (id: Long) -> Unit,
) {
    Scaffold(
        topBar = {
            AppToolbar(
                title = {
                    Text(
                        text = stringResource(R.string.title_favourites),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                },
            )
        }
    ) {
        ContentErrorLoading(
            contentState = ContentState.fromState(
                error = state.error,
                loading = state.loading,
                empty = state.isEmpty,
            ),
            errorContent = {
                Error(
                    onRetryClick = onRetryClick,
                    modifier = Modifier
                        .fillMaxSize(),
                )
            },
            emptyContent = {
                Info(text = stringResource(R.string.label_no_favourites))
            },
            loadingContent = {
                Column(
                    modifier = Modifier
                        .verticalScroll(
                            state = rememberScrollState(),
                            enabled = false,
                        )
                        .padding(
                            start = Space.Medium,
                            end = Space.Medium,
                            top = Space.Medium,
                            bottom = Space.Medium + BottomNavigationHeight
                        ),
                    verticalArrangement = Arrangement.spacedBy(Space.Medium),
                ) {
                    repeat(10) {
                        CharacterSkeleton(insideCard = true)
                    }
                }
            }
        ) {
            LazyColumn(
                contentPadding = PaddingValues(
                    start = Space.Medium,
                    end = Space.Medium,
                    top = Space.Medium,
                    bottom = Space.Medium + BottomNavigationHeight
                ),
                verticalArrangement = Arrangement.spacedBy(Space.Medium)
            ) {
                items(
                    items = state.items,
                    key = {
                        it.id
                    },
                ) { character ->
                    Character(
                        name = character.name.orEmpty(),
                        status = character.status.orEmpty(),
                        avatarUri = character.image,
                        insideCard = true,
                        isFavourite = character.isFavourite,
                        onClick = {
                            onCharacterClick(character.id)
                        },
                    )
                }
            }
        }
    }
}