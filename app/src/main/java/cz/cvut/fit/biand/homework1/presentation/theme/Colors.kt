package cz.cvut.fit.biand.homework1.presentation.theme

import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color

val appLightColors = lightColors(
    primary = ColorsLight.primary,
    primaryVariant = ColorsLight.primaryVariant,
    secondary = ColorsLight.secondary,
    secondaryVariant = ColorsLight.secondaryVariant,
    background = ColorsLight.background,
    surface = ColorsLight.surface,
    onPrimary = ColorsLight.onPrimary,
    onSecondary = ColorsLight.onSecondary,
    onBackground = ColorsLight.onBackground,
    onSurface = ColorsLight.onSurface,
)

val appDarkColors = darkColors(
    primary = ColorsDark.primary,
    primaryVariant = ColorsDark.primaryVariant,
    secondary = ColorsDark.secondary,
    secondaryVariant = ColorsDark.secondaryVariant,
    background = ColorsDark.background,
    surface = ColorsDark.surface,
    onPrimary = ColorsDark.onPrimary,
    onSecondary = ColorsDark.onSecondary,
    onBackground = ColorsDark.onBackground,
    onSurface = ColorsDark.onSurface,
)

private object ColorsLight {
    val primary = Color(0xFFF4F4F9)
    val primaryVariant = Color(0xFFFFFFFF)
    val onPrimary = Color(0xFF000000)
    val secondary = Color(0xFF0000FF)
    val secondaryVariant = Color(0xFF0000FF)
    val onSecondary = Color(0xFFFFFFFF)
    val background = Color(0xFFF4F4F9)
    val onBackground = Color(0xFF000000)
    val surface = Color(0xFFFFFFFF)
    val onSurface = Color(0xFF000000)
}

private object ColorsDark {
    val primary = Color(0xFF181819)
    val primaryVariant = Color(0xFF2E2E2F)
    val onPrimary = Color(0xFFFFFFFF)
    val secondary = Color(0xFF9595FE)
    val secondaryVariant = Color(0xFF9595FE)
    val onSecondary = Color(0xFF000000)
    val background = Color(0xFF181819)
    val onBackground = Color(0xFFFFFFFF)
    val surface = Color(0xFF2E2E2F)
    val onSurface = Color(0xFFFFFFFF)
}