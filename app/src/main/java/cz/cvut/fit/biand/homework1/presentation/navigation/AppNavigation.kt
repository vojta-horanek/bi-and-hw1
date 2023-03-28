package cz.cvut.fit.biand.homework1.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import cz.cvut.fit.biand.homework1.presentation.detail.detailRoute
import cz.cvut.fit.biand.homework1.presentation.detail.navigateToDetail
import cz.cvut.fit.biand.homework1.presentation.overview.overviewRoute
import cz.cvut.fit.biand.homework1.presentation.search.navigateToSearch
import cz.cvut.fit.biand.homework1.presentation.search.searchRoute

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = Routes.Graph.route,
        modifier = modifier,
    ) {
        appNavGraph(
            navController = navController,
        )
    }
}

fun NavGraphBuilder.appNavGraph(
    navController: NavController,
) {
    navigation(
        route = Routes.Graph.route,
        startDestination = Routes.Overview.route,
    ) {
        overviewRoute(
            onNavigateToDetail = { id ->
                navController.navigateToDetail(id)
            },
            onNavigateToSearch = {
                navController.navigateToSearch()
            }
        )
        detailRoute(
            onBackPressed = {
                navController.popBackStack()
            }
        )
        searchRoute(
            onBackPressed = {
                navController.popBackStack()
            }
        )
    }
}