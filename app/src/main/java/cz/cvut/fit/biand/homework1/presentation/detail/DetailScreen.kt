package cz.cvut.fit.biand.homework1.presentation.detail

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import cz.cvut.fit.biand.homework1.presentation.navigation.Routes
import cz.cvut.fit.biand.homework1.presentation.navigation.composableDestination

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
            id = args.id
        )
    }
}

@Composable
internal fun DetailRoute(id: Long) {
    Text(text = id.toString())
}

@Composable
private fun DetailScreen() {
}