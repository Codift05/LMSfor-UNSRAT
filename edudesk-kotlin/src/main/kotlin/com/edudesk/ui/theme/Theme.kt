package com.edudesk.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// IELS Brand colors
val IelsMagenta = Color(0xFFF000FF)
val IelsBlue = Color(0xFF0033FF)
val UdemyDarkPurple = Color(0xFF6A1B9A) // Keeping for subtle dark variants if needed
val BackgroundLight = Color(0xFFF7F9FA)
val SurfaceLight = Color.White
val TextPrimary = Color(0xFF2D2F31)
val TextSecondary = Color(0xFF6A6F73)

private val LightColorScheme =
        lightColorScheme(
                primary = IelsMagenta,
                onPrimary = Color.White,
                primaryContainer = Color(0xFFFFD9FF),
                onPrimaryContainer = Color(0xFF660066),
                secondary = IelsBlue,
                onSecondary = Color.White,
                background = BackgroundLight,
                surface = SurfaceLight,
                onBackground = TextPrimary,
                onSurface = TextPrimary
        )

private val DarkColorScheme =
        darkColorScheme(
                primary = Color(0xFFFF66FF),
                onPrimary = Color(0xFF660066),
                secondary = Color(0xFF668CFF),
                background = Color(0xFF1C1D1F),
                surface = Color(0xFF2D2F31),
                onBackground = Color.White,
                onSurface = Color.White
        )

val Typography =
        Typography(
                displayLarge =
                        TextStyle(
                                fontFamily = FontFamily.SansSerif,
                                fontWeight = FontWeight.Bold,
                                fontSize = 32.sp,
                                color = TextPrimary
                        ),
                headlineMedium =
                        TextStyle(
                                fontFamily = FontFamily.SansSerif,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 24.sp,
                                color = TextPrimary
                        ),
                titleLarge =
                        TextStyle(
                                fontFamily = FontFamily.SansSerif,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 19.sp,
                                color = TextPrimary
                        ),
                bodyLarge =
                        TextStyle(
                                fontFamily = FontFamily.SansSerif,
                                fontWeight = FontWeight.Normal,
                                fontSize = 16.sp,
                                color = TextPrimary
                        ),
                bodyMedium =
                        TextStyle(
                                fontFamily = FontFamily.SansSerif,
                                fontWeight = FontWeight.Normal,
                                fontSize = 14.sp,
                                color = TextSecondary
                        )
        )

@Composable
fun EduDeskTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(colorScheme = colorScheme, typography = Typography, content = content)
}
