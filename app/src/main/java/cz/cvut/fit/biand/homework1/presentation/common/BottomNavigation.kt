package cz.cvut.fit.biand.homework1.presentation.common

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import cz.cvut.fit.biand.homework1.R
import cz.cvut.fit.biand.homework1.presentation.favourite.navigateToFavourites
import cz.cvut.fit.biand.homework1.presentation.navigation.Destination
import cz.cvut.fit.biand.homework1.presentation.navigation.Routes
import cz.cvut.fit.biand.homework1.presentation.overview.navigateToOverview
import cz.cvut.fit.biand.homework1.presentation.theme.Space
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun BottomNavigation(
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val charactersLabel = stringResource(R.string.label_characters)
    val charactersPainter = painterResource(R.drawable.ic_characters)
    val favouritesLabel = stringResource(R.string.label_favourites)
    val favouritePainter = painterResource(R.drawable.ic_favourite_selected)

    val bottomSheetItems = remember {
        listOf(
            BottomSheetItem(
                destination = Routes.Overview,
                label = charactersLabel,
                image = charactersPainter,
                onClick = {
                    navigateToOverview()
                }
            ),
            BottomSheetItem(
                destination = Routes.Favourites,
                label = favouritesLabel,
                image = favouritePainter,
                onClick = {
                    navigateToFavourites()
                }
            ),
        )
    }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val isVisible by remember(currentDestination) {
        derivedStateOf { currentDestination.isBottomNavigationVisible() }
    }

    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Surface(
            color = MaterialTheme.colors.surface,
            elevation = BottomNavigationElevation,
            modifier = modifier,
            shape = MaterialTheme.shapes.medium.copy(
                bottomStart = ZeroCornerSize,
                bottomEnd = ZeroCornerSize,
            ),
        ) {
            Row(
                Modifier
                    .navigationBarsPadding()
                    .fillMaxWidth()
                    .height(BottomNavigationHeight)
                    .selectableGroup()
                    .padding(horizontal = Space.ExtraLarge + Space.Large),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                bottomSheetItems.forEach { item ->
                    BottomNavigationItem(
                        selected = currentDestination.isDestinationSelected(item.destination),
                        label = { Text(text = item.label) },
                        icon = {
                            Icon(
                                painter = item.image,
                                contentDescription = null,
                                modifier = Modifier.size(IconSize)
                            )
                        },
                        onClick = {
                            item.onClick(navController)
                        },
                        selectedContentColor = MaterialTheme.colors.secondary,
                        unselectedContentColor = LocalContentColor.current.copy(alpha = ContentAlpha.medium)
                    )
                }
            }
        }
    }
}

fun NavDestination?.isDestinationSelected(
    destination: Destination
) = this?.hierarchy?.any { it.route == destination.route } == true

fun NavDestination?.isBottomNavigationVisible(
    routes: ImmutableList<Destination> = routesWithBottomNavigation,
) = this?.let { destination ->
    destination.route in routes.map { it.route }
} ?: true

private val routesWithBottomNavigation = persistentListOf<Destination>(
    Routes.Overview,
    Routes.Favourites,
)

private data class BottomSheetItem(
    val destination: Destination,
    val label: String,
    val image: Painter,
    val onClick: NavController.() -> Unit,
)

val BottomNavigationHeight = 56.dp
val BottomNavigationElevation = 8.dp
private val IconSize = 24.dp