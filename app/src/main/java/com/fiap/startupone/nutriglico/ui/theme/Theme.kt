package com.fiap.startupone.nutriglico.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val LightColorScheme = lightColorScheme(
    primary = PrimaryColor,
    onPrimary = TextIconColor,
    primaryContainer = LightPrimaryColor,
    secondary = AccentColor,
    onSecondary = TextIconColor,
    background = Color.White,
    onBackground = PrimaryTextColor,
    surface = Color.White,
    onSurface = SecondaryTextColor,
    error = Color(0xFFB00020),  // Vermelho para erros
    onError = TextIconColor
)

private val DarkColorScheme = darkColorScheme(
    primary = DarkPrimaryColor,
    onPrimary = TextIconColor,
    primaryContainer = PrimaryColor,
    secondary = AccentColor,
    onSecondary = TextIconColor,
    background = Color.Black,
    onBackground = TextIconColor,
    surface = DarkPrimaryColor,
    onSurface = SecondaryTextColor,
    error = Color(0xFFCF6679),  // Vermelho para erros em tema escuro
    onError = TextIconColor
)

@Composable
fun NutriGlicoTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = CustomTypography,
        content = content
    )
}