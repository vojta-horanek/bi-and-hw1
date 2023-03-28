package cz.cvut.fit.biand.homework1.presentation.search

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import cz.cvut.fit.biand.homework1.presentation.navigation.Routes
import cz.cvut.fit.biand.homework1.presentation.navigation.composableDestination

fun NavController.navigateToSearch() {
    navigate(Routes.Search())
}

fun NavGraphBuilder.searchRoute(
    onBackPressed: () -> Unit,
) {
    composableDestination(
        destination = Routes.Search
    ) {
        SearchRoute()
    }
}

@Composable
internal fun SearchRoute() {
}

@Composable
private fun SearchScreen() {
}