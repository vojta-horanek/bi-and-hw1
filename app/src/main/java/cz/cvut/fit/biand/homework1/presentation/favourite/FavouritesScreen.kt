package cz.cvut.fit.biand.homework1.presentation.favourite

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import cz.cvut.fit.biand.homework1.R
import cz.cvut.fit.biand.homework1.presentation.common.Characters
import cz.cvut.fit.biand.homework1.presentation.navigation.Routes
import cz.cvut.fit.biand.homework1.presentation.navigation.composableDestination
import cz.cvut.fit.biand.homework1.presentation.navigation.navigateToBottomNavigationItem
import kotlinx.collections.immutable.persistentListOf

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
) {
    FavouritesScreen(
        onCharacterClick = onNavigateToDetail,
    )
}

@Composable
private fun FavouritesScreen(
    onCharacterClick: (id: Long) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.title_favourites))
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
                .padding(innerPadding),
            insideCard = true,
        )
    }
}