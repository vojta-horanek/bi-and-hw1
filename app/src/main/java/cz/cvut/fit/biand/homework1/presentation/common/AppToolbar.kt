package cz.cvut.fit.biand.homework1.presentation.common

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cz.cvut.fit.biand.homework1.presentation.theme.Space

@Composable
fun AppToolbar(
    modifier: Modifier = Modifier,
    navigationIcon: (@Composable () -> Unit)? = null,
    actions: (@Composable RowScope.() -> Unit)? = null,
    title: @Composable () -> Unit,
) {
    TopAppBar(
        modifier = modifier,
        contentPadding = PaddingValues(
            start = Space.Large,
            end = Space.MediumLarge,
        ),
        content = {
            CompositionLocalProvider(
                LocalContentAlpha provides ContentAlpha.high
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (navigationIcon != null) {
                        navigationIcon()
                        Spacer(modifier = Modifier.width(Space.Medium))
                    }

                    Box(
                        modifier = Modifier.weight(1f)
                    ) {
                        ProvideTextStyle(
                            value = MaterialTheme.typography.h2
                        ) {
                            title()
                        }
                    }

                    if (actions != null) {
                        Spacer(modifier = Modifier.width(Space.Medium))
                        actions()
                    }
                }
            }
        },
    )
}