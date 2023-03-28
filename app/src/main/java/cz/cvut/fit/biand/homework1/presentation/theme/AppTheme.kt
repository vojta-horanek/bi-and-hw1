package cz.cvut.fit.biand.homework1.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun AppTheme(
    isDark: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colors = if (isDark) {
        appDarkColors
    } else {
        appLightColors
    }
    MaterialTheme(
        colors = colors,
        shapes = appShapes,
        typography = appTypography,
        content = content,
    )
}