package cz.cvut.fit.biand.homework1.presentation.common

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandIn
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.delay
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun DelayedExpandingContent(
    modifier: Modifier = Modifier,
    delay: Duration = 50.milliseconds,
    content: @Composable () -> Unit,
) {
    var visible by rememberSaveable {
        mutableStateOf(false)
    }

    LaunchedEffect(visible) {
        if (!visible) {
            delay(delay)
            visible = true
        }
    }

    AnimatedVisibility(
        visible = visible,
        enter = expandIn(
            expandFrom = Alignment.CenterStart,
            animationSpec = spring()
        ),
        modifier = modifier,
    ) {
        content()
    }
}