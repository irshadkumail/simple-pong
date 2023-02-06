package com.irshadkumail.game.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Shapes
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val ColorPalette = lightColors(
    primary = DarkGreen,
    primaryVariant = DarkGreen,
    secondary = DarkGreen,
    background = LightGreen
)

@Composable
fun GameTheme(content: @Composable () -> Unit) {

    val systemUiController = rememberSystemUiController()

    systemUiController.setSystemBarsColor(LightGreen, darkIcons = true)
    MaterialTheme(
        colors = ColorPalette,
        content = content
    )
}