/*
 *abiola 2022
 */

package com.mshdabiola.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * Now in Android theme.
 *
 * @param darkTheme Whether the theme should use a dark color scheme (follows system by default).
 * @param androidTheme Whether the theme should use the Android theme color scheme instead of the
 *        default theme.
 * @param disableDynamicTheming If `true`, disables the use of dynamic theming, even when it is
 *        supported. This parameter has no effect if [androidTheme] is `true`.
 */

lateinit var extendedColorScheme: ExtendedColorScheme

@Composable
fun CbtTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    androidTheme: Boolean = false,
    disableDynamicTheming: Boolean = true,
    content: @Composable () -> Unit,
) {
    extendedColorScheme = if (darkTheme) extendedDark else extendedLight

    val colorScheme = when {
        androidTheme -> if (darkTheme) highContrastDarkColorScheme else highContrastLightColorScheme
        !disableDynamicTheming && supportsDynamicTheming() -> {
            getDynamicColor(darkTheme)
        }

        else -> if (darkTheme) darkScheme else lightScheme
    }
//

    // Gradient colors
    val emptyGradientColors = GradientColors(container = colorScheme.surfaceColorAtElevation(2.dp))
    val defaultGradientColors = GradientColors(
        top = colorScheme.inverseOnSurface,
        bottom = colorScheme.primaryContainer,
        container = colorScheme.surface,
    )
    val gradientColors = when {
        androidTheme -> if (darkTheme) DarkAndroidGradientColors else LightAndroidGradientColors
        !disableDynamicTheming && supportsDynamicTheming() -> emptyGradientColors
        else -> defaultGradientColors
    }
    // Background theme
    val defaultBackgroundTheme = BackgroundTheme(
        color = colorScheme.surface,
        tonalElevation = 2.dp,
    )
    val backgroundTheme = when {
        androidTheme -> if (darkTheme) DarkAndroidBackgroundTheme else LightAndroidBackgroundTheme
        else -> defaultBackgroundTheme
    }
    val tintTheme = when {
        androidTheme -> TintTheme()
        !disableDynamicTheming && supportsDynamicTheming() -> TintTheme(colorScheme.primary)
        else -> TintTheme()
    }
    // Composition locals
    CompositionLocalProvider(
        LocalGradientColors provides gradientColors,
        LocalBackgroundTheme provides backgroundTheme,
        LocalTintTheme provides tintTheme,
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = SkTypography,
            content = content,
        )
    }
}

expect fun supportsDynamicTheming(): Boolean

@Composable
expect fun getDynamicColor(darkTheme: Boolean): ColorScheme

val primaryLight = Color(0xFF116682)
val onPrimaryLight = Color(0xFFFFFFFF)
val primaryContainerLight = Color(0xFFBDE9FF)
val onPrimaryContainerLight = Color(0xFF001F2A)
val secondaryLight = Color(0xFF4D616C)
val onSecondaryLight = Color(0xFFFFFFFF)
val secondaryContainerLight = Color(0xFFD0E6F2)
val onSecondaryContainerLight = Color(0xFF081E27)
val tertiaryLight = Color(0xFF5D5B7D)
val onTertiaryLight = Color(0xFFFFFFFF)
val tertiaryContainerLight = Color(0xFFE3DFFF)
val onTertiaryContainerLight = Color(0xFF191836)
val errorLight = Color(0xFFBA1A1A)
val onErrorLight = Color(0xFFFFFFFF)
val errorContainerLight = Color(0xFFFFDAD6)
val onErrorContainerLight = Color(0xFF410002)
val backgroundLight = Color(0xFFF6FAFD)
val onBackgroundLight = Color(0xFF171C1F)
val surfaceLight = Color(0xFFF6FAFD)
val onSurfaceLight = Color(0xFF171C1F)
val surfaceVariantLight = Color(0xFFDCE4E9)
val onSurfaceVariantLight = Color(0xFF40484C)
val outlineLight = Color(0xFF70787D)
val outlineVariantLight = Color(0xFFC0C8CD)
val scrimLight = Color(0xFF000000)
val inverseSurfaceLight = Color(0xFF2C3134)
val inverseOnSurfaceLight = Color(0xFFEDF1F5)
val inversePrimaryLight = Color(0xFF8BD0EF)
val surfaceDimLight = Color(0xFFD6DBDE)
val surfaceBrightLight = Color(0xFFF6FAFD)
val surfaceContainerLowestLight = Color(0xFFFFFFFF)
val surfaceContainerLowLight = Color(0xFFF0F4F8)
val surfaceContainerLight = Color(0xFFEAEEF2)
val surfaceContainerHighLight = Color(0xFFE4E9EC)
val surfaceContainerHighestLight = Color(0xFFDFE3E7)

val primaryLightMediumContrast = Color(0xFF00495F)
val onPrimaryLightMediumContrast = Color(0xFFFFFFFF)
val primaryContainerLightMediumContrast = Color(0xFF337D99)
val onPrimaryContainerLightMediumContrast = Color(0xFFFFFFFF)
val secondaryLightMediumContrast = Color(0xFF31464F)
val onSecondaryLightMediumContrast = Color(0xFFFFFFFF)
val secondaryContainerLightMediumContrast = Color(0xFF637882)
val onSecondaryContainerLightMediumContrast = Color(0xFFFFFFFF)
val tertiaryLightMediumContrast = Color(0xFF413F60)
val onTertiaryLightMediumContrast = Color(0xFFFFFFFF)
val tertiaryContainerLightMediumContrast = Color(0xFF737195)
val onTertiaryContainerLightMediumContrast = Color(0xFFFFFFFF)
val errorLightMediumContrast = Color(0xFF8C0009)
val onErrorLightMediumContrast = Color(0xFFFFFFFF)
val errorContainerLightMediumContrast = Color(0xFFDA342E)
val onErrorContainerLightMediumContrast = Color(0xFFFFFFFF)
val backgroundLightMediumContrast = Color(0xFFF6FAFD)
val onBackgroundLightMediumContrast = Color(0xFF171C1F)
val surfaceLightMediumContrast = Color(0xFFF6FAFD)
val onSurfaceLightMediumContrast = Color(0xFF171C1F)
val surfaceVariantLightMediumContrast = Color(0xFFDCE4E9)
val onSurfaceVariantLightMediumContrast = Color(0xFF3C4448)
val outlineLightMediumContrast = Color(0xFF586065)
val outlineVariantLightMediumContrast = Color(0xFF747C80)
val scrimLightMediumContrast = Color(0xFF000000)
val inverseSurfaceLightMediumContrast = Color(0xFF2C3134)
val inverseOnSurfaceLightMediumContrast = Color(0xFFEDF1F5)
val inversePrimaryLightMediumContrast = Color(0xFF8BD0EF)
val surfaceDimLightMediumContrast = Color(0xFFD6DBDE)
val surfaceBrightLightMediumContrast = Color(0xFFF6FAFD)
val surfaceContainerLowestLightMediumContrast = Color(0xFFFFFFFF)
val surfaceContainerLowLightMediumContrast = Color(0xFFF0F4F8)
val surfaceContainerLightMediumContrast = Color(0xFFEAEEF2)
val surfaceContainerHighLightMediumContrast = Color(0xFFE4E9EC)
val surfaceContainerHighestLightMediumContrast = Color(0xFFDFE3E7)

val primaryLightHighContrast = Color(0xFF002633)
val onPrimaryLightHighContrast = Color(0xFFFFFFFF)
val primaryContainerLightHighContrast = Color(0xFF00495F)
val onPrimaryContainerLightHighContrast = Color(0xFFFFFFFF)
val secondaryLightHighContrast = Color(0xFF10252E)
val onSecondaryLightHighContrast = Color(0xFFFFFFFF)
val secondaryContainerLightHighContrast = Color(0xFF31464F)
val onSecondaryContainerLightHighContrast = Color(0xFFFFFFFF)
val tertiaryLightHighContrast = Color(0xFF201F3D)
val onTertiaryLightHighContrast = Color(0xFFFFFFFF)
val tertiaryContainerLightHighContrast = Color(0xFF413F60)
val onTertiaryContainerLightHighContrast = Color(0xFFFFFFFF)
val errorLightHighContrast = Color(0xFF4E0002)
val onErrorLightHighContrast = Color(0xFFFFFFFF)
val errorContainerLightHighContrast = Color(0xFF8C0009)
val onErrorContainerLightHighContrast = Color(0xFFFFFFFF)
val backgroundLightHighContrast = Color(0xFFF6FAFD)
val onBackgroundLightHighContrast = Color(0xFF171C1F)
val surfaceLightHighContrast = Color(0xFFF6FAFD)
val onSurfaceLightHighContrast = Color(0xFF000000)
val surfaceVariantLightHighContrast = Color(0xFFDCE4E9)
val onSurfaceVariantLightHighContrast = Color(0xFF1D2529)
val outlineLightHighContrast = Color(0xFF3C4448)
val outlineVariantLightHighContrast = Color(0xFF3C4448)
val scrimLightHighContrast = Color(0xFF000000)
val inverseSurfaceLightHighContrast = Color(0xFF2C3134)
val inverseOnSurfaceLightHighContrast = Color(0xFFFFFFFF)
val inversePrimaryLightHighContrast = Color(0xFFD5F0FF)
val surfaceDimLightHighContrast = Color(0xFFD6DBDE)
val surfaceBrightLightHighContrast = Color(0xFFF6FAFD)
val surfaceContainerLowestLightHighContrast = Color(0xFFFFFFFF)
val surfaceContainerLowLightHighContrast = Color(0xFFF0F4F8)
val surfaceContainerLightHighContrast = Color(0xFFEAEEF2)
val surfaceContainerHighLightHighContrast = Color(0xFFE4E9EC)
val surfaceContainerHighestLightHighContrast = Color(0xFFDFE3E7)

val primaryDark = Color(0xFF8BD0EF)
val onPrimaryDark = Color(0xFF003546)
val primaryContainerDark = Color(0xFF004D64)
val onPrimaryContainerDark = Color(0xFFBDE9FF)
val secondaryDark = Color(0xFFB4CAD6)
val onSecondaryDark = Color(0xFF1F333C)
val secondaryContainerDark = Color(0xFF354A53)
val onSecondaryContainerDark = Color(0xFFD0E6F2)
val tertiaryDark = Color(0xFFC6C2EA)
val onTertiaryDark = Color(0xFF2E2D4D)
val tertiaryContainerDark = Color(0xFF454364)
val onTertiaryContainerDark = Color(0xFFE3DFFF)
val errorDark = Color(0xFFFFB4AB)
val onErrorDark = Color(0xFF690005)
val errorContainerDark = Color(0xFF93000A)
val onErrorContainerDark = Color(0xFFFFDAD6)
val backgroundDark = Color(0xFF0F1417)
val onBackgroundDark = Color(0xFFDFE3E7)
val surfaceDark = Color(0xFF0F1417)
val onSurfaceDark = Color(0xFFDFE3E7)
val surfaceVariantDark = Color(0xFF40484C)
val onSurfaceVariantDark = Color(0xFFC0C8CD)
val outlineDark = Color(0xFF8A9297)
val outlineVariantDark = Color(0xFF40484C)
val scrimDark = Color(0xFF000000)
val inverseSurfaceDark = Color(0xFFDFE3E7)
val inverseOnSurfaceDark = Color(0xFF2C3134)
val inversePrimaryDark = Color(0xFF116682)
val surfaceDimDark = Color(0xFF0F1417)
val surfaceBrightDark = Color(0xFF353A3D)
val surfaceContainerLowestDark = Color(0xFF0A0F11)
val surfaceContainerLowDark = Color(0xFF171C1F)
val surfaceContainerDark = Color(0xFF1B2023)
val surfaceContainerHighDark = Color(0xFF262B2D)
val surfaceContainerHighestDark = Color(0xFF303538)

val primaryDarkMediumContrast = Color(0xFF8FD4F4)
val onPrimaryDarkMediumContrast = Color(0xFF001923)
val primaryContainerDarkMediumContrast = Color(0xFF5399B7)
val onPrimaryContainerDarkMediumContrast = Color(0xFF000000)
val secondaryDarkMediumContrast = Color(0xFFB8CEDA)
val onSecondaryDarkMediumContrast = Color(0xFF031921)
val secondaryContainerDarkMediumContrast = Color(0xFF7F949F)
val onSecondaryContainerDarkMediumContrast = Color(0xFF000000)
val tertiaryDarkMediumContrast = Color(0xFFCAC7EF)
val onTertiaryDarkMediumContrast = Color(0xFF141231)
val tertiaryContainerDarkMediumContrast = Color(0xFF908DB2)
val onTertiaryContainerDarkMediumContrast = Color(0xFF000000)
val errorDarkMediumContrast = Color(0xFFFFBAB1)
val onErrorDarkMediumContrast = Color(0xFF370001)
val errorContainerDarkMediumContrast = Color(0xFFFF5449)
val onErrorContainerDarkMediumContrast = Color(0xFF000000)
val backgroundDarkMediumContrast = Color(0xFF0F1417)
val onBackgroundDarkMediumContrast = Color(0xFFDFE3E7)
val surfaceDarkMediumContrast = Color(0xFF0F1417)
val onSurfaceDarkMediumContrast = Color(0xFFF7FBFF)
val surfaceVariantDarkMediumContrast = Color(0xFF40484C)
val onSurfaceVariantDarkMediumContrast = Color(0xFFC4CCD1)
val outlineDarkMediumContrast = Color(0xFF9CA4A9)
val outlineVariantDarkMediumContrast = Color(0xFF7C8489)
val scrimDarkMediumContrast = Color(0xFF000000)
val inverseSurfaceDarkMediumContrast = Color(0xFFDFE3E7)
val inverseOnSurfaceDarkMediumContrast = Color(0xFF262B2E)
val inversePrimaryDarkMediumContrast = Color(0xFF004E65)
val surfaceDimDarkMediumContrast = Color(0xFF0F1417)
val surfaceBrightDarkMediumContrast = Color(0xFF353A3D)
val surfaceContainerLowestDarkMediumContrast = Color(0xFF0A0F11)
val surfaceContainerLowDarkMediumContrast = Color(0xFF171C1F)
val surfaceContainerDarkMediumContrast = Color(0xFF1B2023)
val surfaceContainerHighDarkMediumContrast = Color(0xFF262B2D)
val surfaceContainerHighestDarkMediumContrast = Color(0xFF303538)

val primaryDarkHighContrast = Color(0xFFF7FBFF)
val onPrimaryDarkHighContrast = Color(0xFF000000)
val primaryContainerDarkHighContrast = Color(0xFF8FD4F4)
val onPrimaryContainerDarkHighContrast = Color(0xFF000000)
val secondaryDarkHighContrast = Color(0xFFF7FBFF)
val onSecondaryDarkHighContrast = Color(0xFF000000)
val secondaryContainerDarkHighContrast = Color(0xFFB8CEDA)
val onSecondaryContainerDarkHighContrast = Color(0xFF000000)
val tertiaryDarkHighContrast = Color(0xFFFEF9FF)
val onTertiaryDarkHighContrast = Color(0xFF000000)
val tertiaryContainerDarkHighContrast = Color(0xFFCAC7EF)
val onTertiaryContainerDarkHighContrast = Color(0xFF000000)
val errorDarkHighContrast = Color(0xFFFFF9F9)
val onErrorDarkHighContrast = Color(0xFF000000)
val errorContainerDarkHighContrast = Color(0xFFFFBAB1)
val onErrorContainerDarkHighContrast = Color(0xFF000000)
val backgroundDarkHighContrast = Color(0xFF0F1417)
val onBackgroundDarkHighContrast = Color(0xFFDFE3E7)
val surfaceDarkHighContrast = Color(0xFF0F1417)
val onSurfaceDarkHighContrast = Color(0xFFFFFFFF)
val surfaceVariantDarkHighContrast = Color(0xFF40484C)
val onSurfaceVariantDarkHighContrast = Color(0xFFF7FBFF)
val outlineDarkHighContrast = Color(0xFFC4CCD1)
val outlineVariantDarkHighContrast = Color(0xFFC4CCD1)
val scrimDarkHighContrast = Color(0xFF000000)
val inverseSurfaceDarkHighContrast = Color(0xFFDFE3E7)
val inverseOnSurfaceDarkHighContrast = Color(0xFF000000)
val inversePrimaryDarkHighContrast = Color(0xFF002E3D)
val surfaceDimDarkHighContrast = Color(0xFF0F1417)
val surfaceBrightDarkHighContrast = Color(0xFF353A3D)
val surfaceContainerLowestDarkHighContrast = Color(0xFF0A0F11)
val surfaceContainerLowDarkHighContrast = Color(0xFF171C1F)
val surfaceContainerDarkHighContrast = Color(0xFF1B2023)
val surfaceContainerHighDarkHighContrast = Color(0xFF262B2D)
val surfaceContainerHighestDarkHighContrast = Color(0xFF303538)

val wrongLight = Color(0xFF904A42)
val onWrongLight = Color(0xFFFFFFFF)
val wrongContainerLight = Color(0xFFFFDAD5)
val onWrongContainerLight = Color(0xFF3B0906)
val rightLight = Color(0xFF38693C)
val onRightLight = Color(0xFFFFFFFF)
val rightContainerLight = Color(0xFFB9F0B8)
val onRightContainerLight = Color(0xFF002107)

val wrongLightMediumContrast = Color(0xFF6E3029)
val onWrongLightMediumContrast = Color(0xFFFFFFFF)
val wrongContainerLightMediumContrast = Color(0xFFAA6057)
val onWrongContainerLightMediumContrast = Color(0xFFFFFFFF)
val rightLightMediumContrast = Color(0xFF1B4C23)
val onRightLightMediumContrast = Color(0xFFFFFFFF)
val rightContainerLightMediumContrast = Color(0xFF4E8051)
val onRightContainerLightMediumContrast = Color(0xFFFFFFFF)

val wrongLightHighContrast = Color(0xFF44100C)
val onWrongLightHighContrast = Color(0xFFFFFFFF)
val wrongContainerLightHighContrast = Color(0xFF6E3029)
val onWrongContainerLightHighContrast = Color(0xFFFFFFFF)
val rightLightHighContrast = Color(0xFF00290A)
val onRightLightHighContrast = Color(0xFFFFFFFF)
val rightContainerLightHighContrast = Color(0xFF1B4C23)
val onRightContainerLightHighContrast = Color(0xFFFFFFFF)

val wrongDark = Color(0xFFFFB4AA)
val onWrongDark = Color(0xFF561E18)
val wrongContainerDark = Color(0xFF73342D)
val onWrongContainerDark = Color(0xFFFFDAD5)
val rightDark = Color(0xFF9ED49D)
val onRightDark = Color(0xFF033912)
val rightContainerDark = Color(0xFF1F5027)
val onRightContainerDark = Color(0xFFB9F0B8)

val wrongDarkMediumContrast = Color(0xFFFFBAB1)
val onWrongDarkMediumContrast = Color(0xFF330403)
val wrongContainerDarkMediumContrast = Color(0xFFCC7B71)
val onWrongContainerDarkMediumContrast = Color(0xFF000000)
val rightDarkMediumContrast = Color(0xFFA2D8A1)
val onRightDarkMediumContrast = Color(0xFF001B05)
val rightContainerDarkMediumContrast = Color(0xFF699D6B)
val onRightContainerDarkMediumContrast = Color(0xFF000000)

val wrongDarkHighContrast = Color(0xFFFFF9F9)
val onWrongDarkHighContrast = Color(0xFF000000)
val wrongContainerDarkHighContrast = Color(0xFFFFBAB1)
val onWrongContainerDarkHighContrast = Color(0xFF000000)
val rightDarkHighContrast = Color(0xFFF0FFEB)
val onRightDarkHighContrast = Color(0xFF000000)
val rightContainerDarkHighContrast = Color(0xFFA2D8A1)
val onRightContainerDarkHighContrast = Color(0xFF000000)

@Immutable
data class ExtendedColorScheme(
    val wrong: ColorFamily,
    val right: ColorFamily,
)

val lightScheme = lightColorScheme(
    primary = primaryLight,
    onPrimary = onPrimaryLight,
    primaryContainer = primaryContainerLight,
    onPrimaryContainer = onPrimaryContainerLight,
    secondary = secondaryLight,
    onSecondary = onSecondaryLight,
    secondaryContainer = secondaryContainerLight,
    onSecondaryContainer = onSecondaryContainerLight,
    tertiary = tertiaryLight,
    onTertiary = onTertiaryLight,
    tertiaryContainer = tertiaryContainerLight,
    onTertiaryContainer = onTertiaryContainerLight,
    error = errorLight,
    onError = onErrorLight,
    errorContainer = errorContainerLight,
    onErrorContainer = onErrorContainerLight,
    background = backgroundLight,
    onBackground = onBackgroundLight,
    surface = surfaceLight,
    onSurface = onSurfaceLight,
    surfaceVariant = surfaceVariantLight,
    onSurfaceVariant = onSurfaceVariantLight,
    outline = outlineLight,
    outlineVariant = outlineVariantLight,
    scrim = scrimLight,
    inverseSurface = inverseSurfaceLight,
    inverseOnSurface = inverseOnSurfaceLight,
    inversePrimary = inversePrimaryLight,
    surfaceDim = surfaceDimLight,
    surfaceBright = surfaceBrightLight,
    surfaceContainerLowest = surfaceContainerLowestLight,
    surfaceContainerLow = surfaceContainerLowLight,
    surfaceContainer = surfaceContainerLight,
    surfaceContainerHigh = surfaceContainerHighLight,
    surfaceContainerHighest = surfaceContainerHighestLight,
)

val darkScheme = darkColorScheme(
    primary = primaryDark,
    onPrimary = onPrimaryDark,
    primaryContainer = primaryContainerDark,
    onPrimaryContainer = onPrimaryContainerDark,
    secondary = secondaryDark,
    onSecondary = onSecondaryDark,
    secondaryContainer = secondaryContainerDark,
    onSecondaryContainer = onSecondaryContainerDark,
    tertiary = tertiaryDark,
    onTertiary = onTertiaryDark,
    tertiaryContainer = tertiaryContainerDark,
    onTertiaryContainer = onTertiaryContainerDark,
    error = errorDark,
    onError = onErrorDark,
    errorContainer = errorContainerDark,
    onErrorContainer = onErrorContainerDark,
    background = backgroundDark,
    onBackground = onBackgroundDark,
    surface = surfaceDark,
    onSurface = onSurfaceDark,
    surfaceVariant = surfaceVariantDark,
    onSurfaceVariant = onSurfaceVariantDark,
    outline = outlineDark,
    outlineVariant = outlineVariantDark,
    scrim = scrimDark,
    inverseSurface = inverseSurfaceDark,
    inverseOnSurface = inverseOnSurfaceDark,
    inversePrimary = inversePrimaryDark,
    surfaceDim = surfaceDimDark,
    surfaceBright = surfaceBrightDark,
    surfaceContainerLowest = surfaceContainerLowestDark,
    surfaceContainerLow = surfaceContainerLowDark,
    surfaceContainer = surfaceContainerDark,
    surfaceContainerHigh = surfaceContainerHighDark,
    surfaceContainerHighest = surfaceContainerHighestDark,
)

private val mediumContrastLightColorScheme = lightColorScheme(
    primary = primaryLightMediumContrast,
    onPrimary = onPrimaryLightMediumContrast,
    primaryContainer = primaryContainerLightMediumContrast,
    onPrimaryContainer = onPrimaryContainerLightMediumContrast,
    secondary = secondaryLightMediumContrast,
    onSecondary = onSecondaryLightMediumContrast,
    secondaryContainer = secondaryContainerLightMediumContrast,
    onSecondaryContainer = onSecondaryContainerLightMediumContrast,
    tertiary = tertiaryLightMediumContrast,
    onTertiary = onTertiaryLightMediumContrast,
    tertiaryContainer = tertiaryContainerLightMediumContrast,
    onTertiaryContainer = onTertiaryContainerLightMediumContrast,
    error = errorLightMediumContrast,
    onError = onErrorLightMediumContrast,
    errorContainer = errorContainerLightMediumContrast,
    onErrorContainer = onErrorContainerLightMediumContrast,
    background = backgroundLightMediumContrast,
    onBackground = onBackgroundLightMediumContrast,
    surface = surfaceLightMediumContrast,
    onSurface = onSurfaceLightMediumContrast,
    surfaceVariant = surfaceVariantLightMediumContrast,
    onSurfaceVariant = onSurfaceVariantLightMediumContrast,
    outline = outlineLightMediumContrast,
    outlineVariant = outlineVariantLightMediumContrast,
    scrim = scrimLightMediumContrast,
    inverseSurface = inverseSurfaceLightMediumContrast,
    inverseOnSurface = inverseOnSurfaceLightMediumContrast,
    inversePrimary = inversePrimaryLightMediumContrast,
    surfaceDim = surfaceDimLightMediumContrast,
    surfaceBright = surfaceBrightLightMediumContrast,
    surfaceContainerLowest = surfaceContainerLowestLightMediumContrast,
    surfaceContainerLow = surfaceContainerLowLightMediumContrast,
    surfaceContainer = surfaceContainerLightMediumContrast,
    surfaceContainerHigh = surfaceContainerHighLightMediumContrast,
    surfaceContainerHighest = surfaceContainerHighestLightMediumContrast,
)

val highContrastLightColorScheme = lightColorScheme(
    primary = primaryLightHighContrast,
    onPrimary = onPrimaryLightHighContrast,
    primaryContainer = primaryContainerLightHighContrast,
    onPrimaryContainer = onPrimaryContainerLightHighContrast,
    secondary = secondaryLightHighContrast,
    onSecondary = onSecondaryLightHighContrast,
    secondaryContainer = secondaryContainerLightHighContrast,
    onSecondaryContainer = onSecondaryContainerLightHighContrast,
    tertiary = tertiaryLightHighContrast,
    onTertiary = onTertiaryLightHighContrast,
    tertiaryContainer = tertiaryContainerLightHighContrast,
    onTertiaryContainer = onTertiaryContainerLightHighContrast,
    error = errorLightHighContrast,
    onError = onErrorLightHighContrast,
    errorContainer = errorContainerLightHighContrast,
    onErrorContainer = onErrorContainerLightHighContrast,
    background = backgroundLightHighContrast,
    onBackground = onBackgroundLightHighContrast,
    surface = surfaceLightHighContrast,
    onSurface = onSurfaceLightHighContrast,
    surfaceVariant = surfaceVariantLightHighContrast,
    onSurfaceVariant = onSurfaceVariantLightHighContrast,
    outline = outlineLightHighContrast,
    outlineVariant = outlineVariantLightHighContrast,
    scrim = scrimLightHighContrast,
    inverseSurface = inverseSurfaceLightHighContrast,
    inverseOnSurface = inverseOnSurfaceLightHighContrast,
    inversePrimary = inversePrimaryLightHighContrast,
    surfaceDim = surfaceDimLightHighContrast,
    surfaceBright = surfaceBrightLightHighContrast,
    surfaceContainerLowest = surfaceContainerLowestLightHighContrast,
    surfaceContainerLow = surfaceContainerLowLightHighContrast,
    surfaceContainer = surfaceContainerLightHighContrast,
    surfaceContainerHigh = surfaceContainerHighLightHighContrast,
    surfaceContainerHighest = surfaceContainerHighestLightHighContrast,
)

private val mediumContrastDarkColorScheme = darkColorScheme(
    primary = primaryDarkMediumContrast,
    onPrimary = onPrimaryDarkMediumContrast,
    primaryContainer = primaryContainerDarkMediumContrast,
    onPrimaryContainer = onPrimaryContainerDarkMediumContrast,
    secondary = secondaryDarkMediumContrast,
    onSecondary = onSecondaryDarkMediumContrast,
    secondaryContainer = secondaryContainerDarkMediumContrast,
    onSecondaryContainer = onSecondaryContainerDarkMediumContrast,
    tertiary = tertiaryDarkMediumContrast,
    onTertiary = onTertiaryDarkMediumContrast,
    tertiaryContainer = tertiaryContainerDarkMediumContrast,
    onTertiaryContainer = onTertiaryContainerDarkMediumContrast,
    error = errorDarkMediumContrast,
    onError = onErrorDarkMediumContrast,
    errorContainer = errorContainerDarkMediumContrast,
    onErrorContainer = onErrorContainerDarkMediumContrast,
    background = backgroundDarkMediumContrast,
    onBackground = onBackgroundDarkMediumContrast,
    surface = surfaceDarkMediumContrast,
    onSurface = onSurfaceDarkMediumContrast,
    surfaceVariant = surfaceVariantDarkMediumContrast,
    onSurfaceVariant = onSurfaceVariantDarkMediumContrast,
    outline = outlineDarkMediumContrast,
    outlineVariant = outlineVariantDarkMediumContrast,
    scrim = scrimDarkMediumContrast,
    inverseSurface = inverseSurfaceDarkMediumContrast,
    inverseOnSurface = inverseOnSurfaceDarkMediumContrast,
    inversePrimary = inversePrimaryDarkMediumContrast,
    surfaceDim = surfaceDimDarkMediumContrast,
    surfaceBright = surfaceBrightDarkMediumContrast,
    surfaceContainerLowest = surfaceContainerLowestDarkMediumContrast,
    surfaceContainerLow = surfaceContainerLowDarkMediumContrast,
    surfaceContainer = surfaceContainerDarkMediumContrast,
    surfaceContainerHigh = surfaceContainerHighDarkMediumContrast,
    surfaceContainerHighest = surfaceContainerHighestDarkMediumContrast,
)

private val highContrastDarkColorScheme = darkColorScheme(
    primary = primaryDarkHighContrast,
    onPrimary = onPrimaryDarkHighContrast,
    primaryContainer = primaryContainerDarkHighContrast,
    onPrimaryContainer = onPrimaryContainerDarkHighContrast,
    secondary = secondaryDarkHighContrast,
    onSecondary = onSecondaryDarkHighContrast,
    secondaryContainer = secondaryContainerDarkHighContrast,
    onSecondaryContainer = onSecondaryContainerDarkHighContrast,
    tertiary = tertiaryDarkHighContrast,
    onTertiary = onTertiaryDarkHighContrast,
    tertiaryContainer = tertiaryContainerDarkHighContrast,
    onTertiaryContainer = onTertiaryContainerDarkHighContrast,
    error = errorDarkHighContrast,
    onError = onErrorDarkHighContrast,
    errorContainer = errorContainerDarkHighContrast,
    onErrorContainer = onErrorContainerDarkHighContrast,
    background = backgroundDarkHighContrast,
    onBackground = onBackgroundDarkHighContrast,
    surface = surfaceDarkHighContrast,
    onSurface = onSurfaceDarkHighContrast,
    surfaceVariant = surfaceVariantDarkHighContrast,
    onSurfaceVariant = onSurfaceVariantDarkHighContrast,
    outline = outlineDarkHighContrast,
    outlineVariant = outlineVariantDarkHighContrast,
    scrim = scrimDarkHighContrast,
    inverseSurface = inverseSurfaceDarkHighContrast,
    inverseOnSurface = inverseOnSurfaceDarkHighContrast,
    inversePrimary = inversePrimaryDarkHighContrast,
    surfaceDim = surfaceDimDarkHighContrast,
    surfaceBright = surfaceBrightDarkHighContrast,
    surfaceContainerLowest = surfaceContainerLowestDarkHighContrast,
    surfaceContainerLow = surfaceContainerLowDarkHighContrast,
    surfaceContainer = surfaceContainerDarkHighContrast,
    surfaceContainerHigh = surfaceContainerHighDarkHighContrast,
    surfaceContainerHighest = surfaceContainerHighestDarkHighContrast,
)

val extendedLight = ExtendedColorScheme(
    wrong = ColorFamily(
        wrongLight,
        onWrongLight,
        wrongContainerLight,
        onWrongContainerLight,
    ),
    right = ColorFamily(
        rightLight,
        onRightLight,
        rightContainerLight,
        onRightContainerLight,
    ),
)

val extendedDark = ExtendedColorScheme(
    wrong = ColorFamily(
        wrongDark,
        onWrongDark,
        wrongContainerDark,
        onWrongContainerDark,
    ),
    right = ColorFamily(
        rightDark,
        onRightDark,
        rightContainerDark,
        onRightContainerDark,
    ),
)

val extendedLightMediumContrast = ExtendedColorScheme(
    wrong = ColorFamily(
        wrongLightMediumContrast,
        onWrongLightMediumContrast,
        wrongContainerLightMediumContrast,
        onWrongContainerLightMediumContrast,
    ),
    right = ColorFamily(
        rightLightMediumContrast,
        onRightLightMediumContrast,
        rightContainerLightMediumContrast,
        onRightContainerLightMediumContrast,
    ),
)

val extendedLightHighContrast = ExtendedColorScheme(
    wrong = ColorFamily(
        wrongLightHighContrast,
        onWrongLightHighContrast,
        wrongContainerLightHighContrast,
        onWrongContainerLightHighContrast,
    ),
    right = ColorFamily(
        rightLightHighContrast,
        onRightLightHighContrast,
        rightContainerLightHighContrast,
        onRightContainerLightHighContrast,
    ),
)

val extendedDarkMediumContrast = ExtendedColorScheme(
    wrong = ColorFamily(
        wrongDarkMediumContrast,
        onWrongDarkMediumContrast,
        wrongContainerDarkMediumContrast,
        onWrongContainerDarkMediumContrast,
    ),
    right = ColorFamily(
        rightDarkMediumContrast,
        onRightDarkMediumContrast,
        rightContainerDarkMediumContrast,
        onRightContainerDarkMediumContrast,
    ),
)

val extendedDarkHighContrast = ExtendedColorScheme(
    wrong = ColorFamily(
        wrongDarkHighContrast,
        onWrongDarkHighContrast,
        wrongContainerDarkHighContrast,
        onWrongContainerDarkHighContrast,
    ),
    right = ColorFamily(
        rightDarkHighContrast,
        onRightDarkHighContrast,
        rightContainerDarkHighContrast,
        onRightContainerDarkHighContrast,
    ),
)

@Immutable
data class ColorFamily(
    val color: Color,
    val onColor: Color,
    val colorContainer: Color,
    val onColorContainer: Color,
)

val unspecified_scheme = ColorFamily(
    Color.Unspecified,
    Color.Unspecified,
    Color.Unspecified,
    Color.Unspecified,
)

val LightAndroidGradientColors = GradientColors(container = primaryContainerLight)

/**
 * Dark Android gradient colors
 */
val DarkAndroidGradientColors = GradientColors(container = Color.Black)

/**
 * Light Android background theme
 */
val LightAndroidBackgroundTheme = BackgroundTheme(color = primaryContainerDark)

/**
 * Dark Android background theme
 */
val DarkAndroidBackgroundTheme = BackgroundTheme(color = Color.Black)
