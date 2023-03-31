package cz.cvut.fit.biand.homework1.presentation.overview

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import cz.cvut.fit.biand.homework1.R
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
    val state by viewModel.collectState()

    LaunchedEffect(Unit) {
        viewModel.onIntent(OverviewViewModel.Intent.OnViewInitialized)
    }

    OverviewScreen(
        state = state,
        onSearchClick = onNavigateToSearch,
        onCharacterClick = onNavigateToDetail,
        onRetryClick = {
            viewModel.onIntent(OverviewViewModel.Intent.OnRetryClick)
        }
    )
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
private fun OverviewScreen(
    state: OverviewViewModel.State,
    onSearchClick: () -> Unit,
    onRetryClick: () -> Unit,
    onCharacterClick: (id: Long) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                content = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(
                                start = Space.Large,
                            )
                    ) {
                        CompositionLocalProvider(
                            LocalContentAlpha provides ContentAlpha.high
                        ) {
                            Text(
                                text = stringResource(R.string.title_characters),
                                style = MaterialTheme.typography.h2,
                                modifier = Modifier
                                    .weight(1f)
                            )
                            IconButton(
                                onClick = onSearchClick,
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.ic_search),
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
                loading = state.loading,
                empty = state.isEmpty,
            ),
            errorContent = {
                Error(
                    onRetryClick = onRetryClick,
                )
            },
            emptyContent = {
                Info(text = stringResource(R.string.label_no_characters))
            }
        ) {
            Characters(
                characters = state.items,
                onCharacterClick = { id ->
                    onCharacterClick(id)
                },
                contentPadding = PaddingValues(
                    start = Space.Medium,
                    end = Space.Medium,
                    top = Space.Medium,
                    bottom = Space.Medium + BottomNavigationHeight
                ),
                insideCard = true,
            )
        }
    }
}