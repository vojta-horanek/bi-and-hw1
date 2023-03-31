package cz.cvut.fit.biand.homework1.presentation.search

import android.annotation.SuppressLint
import androidx.compose.animation.*
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import cz.cvut.fit.biand.homework1.R
import cz.cvut.fit.biand.homework1.presentation.common.*
import cz.cvut.fit.biand.homework1.presentation.navigation.Routes
import cz.cvut.fit.biand.homework1.presentation.navigation.composableDestination
import cz.cvut.fit.biand.homework1.presentation.theme.Space
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel

fun NavController.navigateToSearch() {
    navigate(Routes.Search())
}

fun NavGraphBuilder.searchRoute(
    onBackPressed: () -> Unit,
    onNavigateToDetail: (id: Long) -> Unit,
) {
    composableDestination(
        destination = Routes.Search
    ) {
        SearchRoute(
            onBackPressed = onBackPressed,
            onNavigateToDetail = onNavigateToDetail,
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
internal fun SearchRoute(
    onBackPressed: () -> Unit,
    onNavigateToDetail: (id: Long) -> Unit,
    viewModel: SearchViewModel = koinViewModel(),
) {
    val state by viewModel.collectState()
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    SearchScreen(
        state = state,
        onBackPressed = onBackPressed,
        onCharacterClick = onNavigateToDetail,
        focusRequester = focusRequester,
        onQueryChanged = { query ->
            viewModel.onIntent(SearchViewModel.Intent.OnQueryChanged(query))
        },
        onImeAction = {
            keyboardController?.hide()
        },
        onClearClick = {
            viewModel.onIntent(SearchViewModel.Intent.OnClearClick)
        },
        onRetryClick = {
            viewModel.onIntent(SearchViewModel.Intent.OnRetryClick)
        }
    )
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
private fun SearchScreen(
    state: SearchViewModel.State,
    focusRequester: FocusRequester,
    onBackPressed: () -> Unit,
    onCharacterClick: (id: Long) -> Unit,
    onQueryChanged: (String) -> Unit,
    onImeAction: () -> Unit,
    onClearClick: () -> Unit,
    onRetryClick: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                content = {
                    DelayedExpandingContent {
                        IconButton(onClick = onBackPressed) {
                            Icon(
                                painter = painterResource(R.drawable.ic_arrow_left),
                                contentDescription = null,
                            )
                        }
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Box(
                            contentAlignment = Alignment.CenterStart,
                            modifier = Modifier
                                .weight(1f),
                        ) {
                            BasicTextField(
                                value = state.query,
                                onValueChange = onQueryChanged,
                                singleLine = true,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .focusRequester(focusRequester),
                                keyboardOptions = KeyboardOptions.Default.copy(
                                    imeAction = ImeAction.Search,
                                ),
                                keyboardActions = KeyboardActions {
                                    onImeAction()
                                },
                                cursorBrush = SolidColor(MaterialTheme.colors.secondary),
                            )
                            if (state.isLabelVisible) {
                                Text(
                                    text = stringResource(R.string.title_search_characters),
                                    modifier = Modifier
                                )
                            }
                        }
                        AnimatedVisibility(
                            visible = state.isClearVisible,
                            enter = fadeIn(),
                            exit = fadeOut(),
                        ) {
                            IconButton(onClick = onClearClick) {
                                Icon(
                                    painter = painterResource(R.drawable.ic_clear),
                                    contentDescription = null,
                                )
                            }
                        }
                    }
                },
            )
        }
    ) {
        ContentErrorLoading(
            contentState = ContentState.fromState(
                error = state.error,
                empty = state.isNoResultsVisible,
                loading = state.loading,
            ),
            errorContent = {
                Error(
                    onRetryClick = onRetryClick,
                )
            },
            emptyContent = {
                Info(text = stringResource(R.string.label_no_characters_found))
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
                    bottom = Space.Medium,
                ),
                insideCard = false,
            )
        }
    }
}