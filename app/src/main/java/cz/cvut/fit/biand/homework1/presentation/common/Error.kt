package cz.cvut.fit.biand.homework1.presentation.common

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import cz.cvut.fit.biand.homework1.R
import cz.cvut.fit.biand.homework1.presentation.theme.Space

@Composable
fun Error(
    modifier: Modifier = Modifier,
    onRetryClick: (() -> Unit)? = null,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize(),
    ) {
        CompositionLocalProvider(
            LocalContentAlpha provides ContentAlpha.medium,
        ) {
            Text(
                text = stringResource(R.string.label_error_occurred),
                style = MaterialTheme.typography.h4
            )
        }

        Spacer(modifier = Modifier.height(Space.Large))

        onRetryClick?.let { onClick ->
            Button(
                onClick = onClick,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.secondary,
                )
            ) {
                Text(text = stringResource(R.string.button_retry))
            }
        }

    }
}