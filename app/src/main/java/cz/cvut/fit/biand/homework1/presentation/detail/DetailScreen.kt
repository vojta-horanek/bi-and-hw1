package cz.cvut.fit.biand.homework1.presentation.detail

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import cz.cvut.fit.biand.homework1.presentation.navigation.Routes
import cz.cvut.fit.biand.homework1.presentation.navigation.composableDestination

fun NavController.navigateToDetail(id: Long) {

}

fun NavGraphBuilder.detailRoute(
    onBackPressed: () -> Unit,
) {
    composableDestination(
        destination = Routes.Detail
    ) {
        DetailRoute()
    }
}

@Composable
internal fun DetailRoute() {
}

@Composable
private fun DetailScreen() {
}