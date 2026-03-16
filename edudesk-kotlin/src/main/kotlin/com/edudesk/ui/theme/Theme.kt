package com.edudesk.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.platform.Font
import androidx.compose.ui.unit.sp

// IELS Brand colors
val IelsMagenta = Color(0xFFF000FF)
val IelsBlue = Color(0xFF496E96)
val UdemyDarkPurple = Color(0xFF6A1B9A) // Keeping for subtle dark variants if needed
val BackgroundLight = Color(0xFFE2E8EF)
val SurfaceLight = Color.White
val TextPrimary = Color(0xFF2D2F31)
val TextSecondary = Color(0xFF6A6F73)
val UdemyPurple = Color(0xFF5624D0)
val UdemyLightPurple = Color(0xFFC0C4FC)

private val LightColorScheme =
        lightColorScheme(
                primary = IelsBlue,
                onPrimary = Color.White,
                primaryContainer = Color(0xFFD6E4F7),
                onPrimaryContainer = Color(0xFF1A3A5C),
                secondary = IelsBlue,
                onSecondary = Color.White,
                background = BackgroundLight,
                surface = SurfaceLight,
                surfaceTint = Color.Transparent,
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

// Load Inter font family
val InterFontFamily = FontFamily(
    Font("fonts/Inter-Regular.otf", FontWeight.Normal),
    Font("fonts/Inter-Medium.otf", FontWeight.Medium),
    Font("fonts/Inter-SemiBold.otf", FontWeight.SemiBold),
    Font("fonts/Inter-Bold.otf", FontWeight.Bold)
)

val Typography =
        Typography(
                displayLarge =
                        TextStyle(
                                fontFamily = InterFontFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 32.sp,
                                color = TextPrimary
                        ),
                headlineMedium =
                        TextStyle(
                                fontFamily = InterFontFamily,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 24.sp,
                                color = TextPrimary
                        ),
                headlineSmall =
                        TextStyle(
                                fontFamily = InterFontFamily,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 20.sp,
                                color = TextPrimary
                        ),
                titleLarge =
                        TextStyle(
                                fontFamily = InterFontFamily,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 19.sp,
                                color = TextPrimary
                        ),
                titleMedium =
                        TextStyle(
                                fontFamily = InterFontFamily,
                                fontWeight = FontWeight.Medium,
                                fontSize = 16.sp,
                                color = TextPrimary
                        ),
                bodyLarge =
                        TextStyle(
                                fontFamily = InterFontFamily,
                                fontWeight = FontWeight.Normal,
                                fontSize = 16.sp,
                                color = TextPrimary
                        ),
                bodyMedium =
                        TextStyle(
                                fontFamily = InterFontFamily,
                                fontWeight = FontWeight.Normal,
                                fontSize = 14.sp,
                                color = TextSecondary
                        ),
                labelLarge =
                        TextStyle(
                                fontFamily = InterFontFamily,
                                fontWeight = FontWeight.Medium,
                                fontSize = 14.sp,
                                color = TextPrimary
                        ),
                labelMedium =
                        TextStyle(
                                fontFamily = InterFontFamily,
                                fontWeight = FontWeight.Normal,
                                fontSize = 12.sp,
                                color = TextSecondary
                        )
        )

@Composable
fun EduDeskTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(colorScheme = colorScheme, typography = Typography, content = content)
}
