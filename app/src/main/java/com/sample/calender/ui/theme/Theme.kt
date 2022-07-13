package com.sample.calender.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = blue,
    primaryVariant = dark_blue,
    secondary = light_blue,
    onPrimary = Color.White,
    secondaryVariant = Color.Black,
    surface = Dark_night,
    onSecondary = Gray_neutral


)

private val LightColorPalette = lightColors(
    primary = light_blue,
    primaryVariant = blue,
    secondary = dark_blue,
    onPrimary = Color.Black,
    secondaryVariant = Color.Black,
    surface = Gray_day,
    onSecondary = Gray_neutral

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun CalenderTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}