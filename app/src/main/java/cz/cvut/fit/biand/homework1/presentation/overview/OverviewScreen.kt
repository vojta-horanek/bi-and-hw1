package cz.cvut.fit.biand.homework1.presentation.overview

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import cz.cvut.fit.biand.homework1.R
import cz.cvut.fit.biand.homework1.presentation.common.Characters
import cz.cvut.fit.biand.homework1.presentation.navigation.Routes
import cz.cvut.fit.biand.homework1.presentation.navigation.composableDestination
import cz.cvut.fit.biand.homework1.presentation.navigation.navigateToBottomNavigationItem
import kotlinx.collections.immutable.persistentListOf

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
) {
    OverviewScreen(
        onSearchClick = onNavigateToSearch,
        onCharacterClick = onNavigateToDetail,
    )
}

@Composable
private fun OverviewScreen(
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
    ) { innerPadding ->
        Characters(
            characters = persistentListOf(),
            onCharacterClick = { id ->
                onCharacterClick(id)
            },
            modifier = Modifier
                .padding(innerPadding)
        )
    }
}