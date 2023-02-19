package com.zxcursed.wallpaper.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val DarkColorPalette = darkColors(
    primary = Blue100,
    primaryVariant = Purple700,
    secondary = Teal200,
    background = MyGrayForBackground,
    surface = MyGray,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.White,
    onSurface = Color.White
)

private val LightColorPalette = lightColors(
    primary = Purple500,
    primaryVariant = Purple700,
    secondary = Teal200

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
fun FirebaseTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val systemUiController = rememberSystemUiController()
//    val colors = if (darkTheme) {
//        systemUiController.setSystemBarsColor(color = MyGray)
//        systemUiController.setNavigationBarColor(color = MyGray)
//        DarkColorPalette
//    } else {
//        LightColorPalette
//    }
    systemUiController.setSystemBarsColor(color = MyGray)
    systemUiController.setNavigationBarColor(color = MyGray)
    val colors = DarkColorPalette


    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}