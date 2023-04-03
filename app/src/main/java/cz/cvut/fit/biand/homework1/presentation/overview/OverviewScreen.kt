package cz.cvut.fit.biand.homework1.presentation.overview

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import cz.cvut.fit.biand.homework1.R
import cz.cvut.fit.biand.homework1.domain.model.Character
import cz.cvut.fit.biand.homework1.presentation.common.*
import cz.cvut.fit.biand.homework1.presentation.navigation.Routes
import cz.cvut.fit.biand.homework1.presentation.navigation.composableDestination
import cz.cvut.fit.biand.homework1.presentation.navigation.navigateToBottomNavigationItem
import cz.cvut.fit.biand.homework1.presentation.theme.IconSize
import cz.cvut.fit.biand.homework1.presentation.theme.Space
import org.koin.androidx.compose.koinViewModel

fun NavController.navigateToOverview() {
    navigateToBottomNavigationItem(Routes.Overview())
}

fun NavGraphBuilder.overviewRoute(
    onNavigateToSearch: () -> Unit,
    onNavigateToDetail: (id: Long) -> Unit,
) {
    composableDestination(
        destination = Routes.Overview
    ) {
        OverviewRoute(
            onNavigateToSearch = onNavigateToSearch,
            onNavigateToDetail = onNavigateToDetail,
        )
    }
}

@Composable
internal fun OverviewRoute(
    onNavigateToSearch: () -> Unit,
    onNavigateToDetail: (id: Long) -> Unit,
    viewModel: OverviewViewModel = koinViewModel(),
) {
    val characters = viewModel.characters.collectAsLazyPagingItems()

    OverviewScreen(
        onSearchClick = onNavigateToSearch,
        onCharacterClick = onNavigateToDetail,
        characters = characters,
        onRetryClick = {
            viewModel.onIntent(OverviewViewModel.Intent.OnRetryClick)
        },
    )
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
private fun OverviewScreen(
    characters: LazyPagingItems<Character>,
    onSearchClick: () -> Unit,
    onRetryClick: () -> Unit,
    onCharacterClick: (id: Long) -> Unit,
) {
    Scaffold(
        topBar = {
            AppToolbar(
                title = {
                    Text(
                        text = stringResource(R.string.title_characters),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                },
                actions = {
                    IconButton(
                        onClick = onSearchClick,
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_search),
                            contentDescription = null,
                            modifier = Modifier.size(IconSize.Medium)
                        )
                    }
                },
            )
        }
    ) {
        LazyPagingItemsScreen(
            contentPadding = PaddingValues(
                start = Space.Medium,
                end = Space.Medium,
                top = Space.Medium,
                bottom = Space.Medium + BottomNavigationHeight
            ),
            modifier = Modifier
                .fillMaxSize(),
            lazyPagingItems = characters,
            emptyContent = {
                Info(text = stringResource(R.string.label_no_characters))
            },
            errorContent = {
                Error(
                    onRetryClick = onRetryClick,
                    modifier = Modifier
                        .fillMaxSize()
                )
            },
            loadingContent = { contentPadding ->
                Column(
                    verticalArrangement = Arrangement.spacedBy(Space.Medium),
                    modifier = Modifier
                        .verticalScroll(
                            state = rememberScrollState(),
                            enabled = false,
                        )
                        .padding(contentPadding),
                ) {
                    repeat(10) {
                        CharacterSkeleton(
                            insideCard = true,
                        )
                    }
                }
            },
            appendErrorItem = {
                item {
                    Error(
                        onRetryClick = onRetryClick,
                    )
                }
            },
            appendLoadingItem = {
                items(3) {
                    CharacterSkeleton(
                        insideCard = true,
                    )
                }
            }
        ) { padding, appendContent ->
            LazyColumn(
                contentPadding = padding,
                verticalArrangement = Arrangement.spacedBy(Space.Medium)
            ) {
                items(
                    items = characters,
                    key = {
                        it.id
                    },
                ) { character ->
                    if (character == null) {
                        return@items
                    }
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
                appendContent()
            }

        }

    }
}