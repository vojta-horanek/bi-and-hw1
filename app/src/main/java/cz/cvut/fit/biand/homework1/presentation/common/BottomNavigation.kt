package cz.cvut.fit.biand.homework1.presentation.common

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import cz.cvut.fit.biand.homework1.R
import cz.cvut.fit.biand.homework1.presentation.navigation.Destination
import cz.cvut.fit.biand.homework1.presentation.navigation.Routes
import cz.cvut.fit.biand.homework1.presentation.theme.Space

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
            ),
            BottomSheetItem(
                destination = Routes.Favourites,
                label = favouritesLabel,
                image = favouritePainter,
            ),
        )
    }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    // TODO: Visibility

    Surface(
        color = MaterialTheme.colors.surface,
        elevation = 8.dp, // TODO: Check
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
                .padding(horizontal = Space.ExtraLarge),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            bottomSheetItems.forEach { item ->
                BottomNavigationItem(
                    selected = currentDestination?.hierarchy?.any { it.route == item.destination.route } == true,
                    onClick = {
                        navController.navigate(item.destination()) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = {
                        Icon(
                            painter = item.image,
                            contentDescription = null,
                            modifier = Modifier.size(IconSize)
                        )
                    },
                    label = { Text(text = item.label) },
                    selectedContentColor = MaterialTheme.colors.secondary,
                    unselectedContentColor = LocalContentColor.current.copy(alpha = ContentAlpha.medium)
                )
            }
        }
    }
}

data class BottomSheetItem(
    val destination: Destination,
    val label: String,
    val image: Painter,
)

private val BottomNavigationHeight = 56.dp
private val IconSize = 24.dp