package com.iels.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.platform.Font
import androidx.compose.ui.unit.sp

// --- WARNA PALING ATAS ---
val TextPrimary = Color(0xFF2D2F31)
val TextSecondary = Color(0xFF6A6F73)
val BackgroundLight = Color.White
val SurfaceLight = Color.White
val IelsMagenta = Color(0xFFF000FF)
val IelsBlue = Color(0xFF496E96)
val UdemyDarkPurple = Color(0xFF6A1B9A)
val UdemyPurple = Color(0xFF5624D0)
val UdemyLightPurple = Color(0xFFC0C4FC)

// Untuk ProfileScreen
val BackgroundPage = BackgroundLight
val TextDark = TextPrimary
val TextMuted = TextSecondary
val BorderColor = Color(0xFFE0E0E0)

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
val InterFontFamily =
        FontFamily(
                Font("fonts/Inter-Regular.otf", FontWeight.Normal),
                Font("fonts/Inter-Medium.otf", FontWeight.Medium),
                Font("fonts/Inter-SemiBold.otf", FontWeight.SemiBold),
                Font("fonts/Inter-Bold.otf", FontWeight.Bold),
                Font("fonts/Inter-Bold.otf", FontWeight.ExtraBold), // Fallback for ExtraBold
                Font("fonts/Inter-Regular.otf", FontWeight.Light)   // Fallback for Light
        )

private val defaultTypography = androidx.compose.material3.Typography()

val Typography =
        androidx.compose.material3.Typography(
                displayLarge = defaultTypography.displayLarge.copy(fontFamily = InterFontFamily),
                displayMedium = defaultTypography.displayMedium.copy(fontFamily = InterFontFamily),
                displaySmall = defaultTypography.displaySmall.copy(fontFamily = InterFontFamily),
                headlineLarge = defaultTypography.headlineLarge.copy(fontFamily = InterFontFamily),
                headlineMedium = defaultTypography.headlineMedium.copy(fontFamily = InterFontFamily),
                headlineSmall = defaultTypography.headlineSmall.copy(fontFamily = InterFontFamily),
                titleLarge = defaultTypography.titleLarge.copy(fontFamily = InterFontFamily),
                titleMedium = defaultTypography.titleMedium.copy(fontFamily = InterFontFamily),
                titleSmall = defaultTypography.titleSmall.copy(fontFamily = InterFontFamily),
                bodyLarge = defaultTypography.bodyLarge.copy(fontFamily = InterFontFamily),
                bodyMedium = defaultTypography.bodyMedium.copy(fontFamily = InterFontFamily),
                bodySmall = defaultTypography.bodySmall.copy(fontFamily = InterFontFamily),
                labelLarge = defaultTypography.labelLarge.copy(fontFamily = InterFontFamily),
                labelMedium = defaultTypography.labelMedium.copy(fontFamily = InterFontFamily),
                labelSmall = defaultTypography.labelSmall.copy(fontFamily = InterFontFamily)
        )

@Composable
fun IelsTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
        val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

        MaterialTheme(colorScheme = colorScheme, typography = Typography, content = content)
}
