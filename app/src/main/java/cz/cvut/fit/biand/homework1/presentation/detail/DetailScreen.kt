package cz.cvut.fit.biand.homework1.presentation.detail

import android.annotation.SuppressLint
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import cz.cvut.fit.biand.homework1.R
import cz.cvut.fit.biand.homework1.presentation.common.CharacterDetail
import cz.cvut.fit.biand.homework1.presentation.navigation.Routes
import cz.cvut.fit.biand.homework1.presentation.navigation.composableDestination
import cz.cvut.fit.biand.homework1.presentation.theme.Space
import org.koin.androidx.compose.koinViewModel

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
            id = args.id,
            onBackPressed = onBackPressed,
        )
    }
}

@Composable
internal fun DetailRoute(
    id: Long,
    onBackPressed: () -> Unit,
    viewModel: DetailViewModel = koinViewModel(),
) {
    val state by viewModel.collectState()

    LaunchedEffect(id) {
        viewModel.onIntent(DetailViewModel.Intent.OnViewInitialized(id))
    }

    DetailScreen(
        state = state,
        onBackPressed = onBackPressed,
        onFavouriteClick = {
            viewModel.onIntent(DetailViewModel.Intent.OnFavouriteClick)
        }
    )
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
private fun DetailScreen(
    state: DetailViewModel.State,
    onFavouriteClick: () -> Unit,
    onBackPressed: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                content = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        CompositionLocalProvider(
                            LocalContentAlpha provides ContentAlpha.high
                        ) {
                            IconButton(onClick = onBackPressed) {
                                Icon(
                                    painter = painterResource(R.drawable.ic_arrow_left),
                                    contentDescription = null,
                                )
                            }

                            Text(
                                text = state.character?.name.orEmpty(),
                                style = MaterialTheme.typography.h2,
                                modifier = Modifier
                                    .weight(1f),
                            )

                            IconButton(onClick = onFavouriteClick) {
                                Crossfade(
                                    targetState = state.isFavourite
                                ) { isFavourite ->
                                    val painter = if (isFavourite) {
                                        painterResource(R.drawable.ic_favourite_selected)
                                    } else {
                                        painterResource(R.drawable.ic_favourite)
                                    }

                                    val tint = if (isFavourite) {
                                        MaterialTheme.colors.secondary
                                    } else {
                                        MaterialTheme.colors.onPrimary
                                    }

                                    Icon(
                                        painter = painter,
                                        tint = tint,
                                        contentDescription = null,
                                    )
                                }
                            }
                        }
                    }
                }
            )
        }
    ) {
        if (state.character != null) {
            CharacterDetail(
                character = state.character,
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(Space.Medium)
            )
        }
    }
}
