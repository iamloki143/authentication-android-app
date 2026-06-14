package com.example.authapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// ── Purple brand palette ───────────────────────────────
val Purple50  = Color(0xFFEEEDFE)
val Purple200 = Color(0xFFAFA9EC)
val Purple400 = Color(0xFF7F77DD)
val Purple600 = Color(0xFF534AB7)
val Purple800 = Color(0xFF3C3489)

val Teal50    = Color(0xFFE1F5EE)
val Teal400   = Color(0xFF1D9E75)
val Teal600   = Color(0xFF0F6E56)

val Coral50   = Color(0xFFFAECE7)
val Coral400  = Color(0xFFD85A30)
val Coral600  = Color(0xFF993C1D)

val Amber50   = Color(0xFFFAEEDA)
val Amber400  = Color(0xFFEF9F27)
val Amber600  = Color(0xFF854F0B)
val Amber800  = Color(0xFF633806)

val Red50     = Color(0xFFFCEBEB)
val Red600    = Color(0xFFA32D2D)

val Surface   = Color(0xFFF8F7FF)
val OnSurface = Color(0xFF1C1B1F)

private val LightColors = lightColorScheme(
    primary       = Purple600,
    onPrimary     = Color.White,
    primaryContainer   = Purple50,
    onPrimaryContainer = Purple800,
    secondary     = Teal400,
    onSecondary   = Color.White,
    secondaryContainer   = Teal50,
    onSecondaryContainer = Teal600,
    error         = Coral400,
    onError       = Color.White,
    errorContainer   = Coral50,
    onErrorContainer = Coral600,
    background    = Color(0xFFFDFCFF),
    onBackground  = OnSurface,
    surface       = Color.White,
    onSurface     = OnSurface,
    surfaceVariant    = Color(0xFFF1EFE8),
    onSurfaceVariant  = Color(0xFF5F5E5A),
    outline       = Color(0xFFB4B2A9),
)

private val DarkColors = darkColorScheme(
    primary       = Purple200,
    onPrimary     = Purple800,
    primaryContainer   = Purple800,
    onPrimaryContainer = Purple50,
    secondary     = Teal400,
    onSecondary   = Color(0xFF04342C),
    secondaryContainer   = Color(0xFF085041),
    onSecondaryContainer = Teal50,
    error         = Coral400,
    onError       = Color(0xFF4A1B0C),
    errorContainer   = Coral600,
    onErrorContainer = Coral50,
    background    = Color(0xFF1C1B1F),
    onBackground  = Color(0xFFE6E1E5),
    surface       = Color(0xFF1C1B1F),
    onSurface     = Color(0xFFE6E1E5),
    surfaceVariant    = Color(0xFF2C2C2A),
    onSurfaceVariant  = Color(0xFFD3D1C7),
    outline       = Color(0xFF5F5E5A),
)

@Composable
fun AuthAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        typography  = Typography(),
        content     = content
    )
}
