package cz.cvut.fit.biand.homework1.presentation.overview

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import cz.cvut.fit.biand.homework1.R
import cz.cvut.fit.biand.homework1.presentation.common.BottomNavigationHeight
import cz.cvut.fit.biand.homework1.presentation.common.Characters
import cz.cvut.fit.biand.homework1.presentation.navigation.Routes
import cz.cvut.fit.biand.homework1.presentation.navigation.composableDestination
import cz.cvut.fit.biand.homework1.presentation.navigation.navigateToBottomNavigationItem
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
    )
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
private fun OverviewScreen(
    state: OverviewViewModel.State,
    onSearchClick: () -> Unit,
    onCharacterClick: (id: Long) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                actions = {
                    IconButton(onClick = onSearchClick) {
                        Icon(
                            painter = painterResource(R.drawable.ic_search),
                            contentDescription = null,
                        )
                    }
                },
                title = {
                    Text(text = stringResource(R.string.title_characters))
                },
            )
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
            )
        )
    }
}