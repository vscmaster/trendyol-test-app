package com.trendyol.vsh.interview.project.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

private val LightThemeColors = lightColors(
    primary = BlackMagenta700,
    primaryVariant = Red900,
    onPrimary = Color.White,
    secondary = Red700,
    secondaryVariant = Red900,
    onSecondary = Color.White,
    error = Red800,
    onBackground = Color.Black,

    )

private val DarkThemeColors = darkColors(
    primary = BlackMagenta200,
    primaryVariant = Red700,
    onPrimary = Color.Black,
    secondary = Red300,
    onSecondary = Color.Black,
    error = Red200,
    onBackground = Color.White,
)

private val LightColorPalette = TrendyolColors(
    brand = Magenta900,
    brandSecondary = Red700,
    error = Red700,
    isDark = false,
    placeHolder = LightGray200,
    placeHolderHighlight = White,
    rating = Blue900,
    textHelp = LightGray400,
    uiBackground = LightGray200,
    uiBorder = LightGray400,
)

private val DarkColorPalette = TrendyolColors(
    brand = Magenta200,
    brandSecondary = Red200,
    error = Red200,
    isDark = true,
    placeHolder = LightGray200,
    placeHolderHighlight = White,
    rating = Blue200,
    textHelp = LightGray400,
    uiBackground = LightGray400,
    uiBorder = LightGray700,
)

val TrendyolTypography = Typography(
    h4 = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 30.sp,
        letterSpacing = 0.sp
    ),
    h5 = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
        letterSpacing = 0.sp
    ),
    h6 = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        letterSpacing = 0.sp
    ),
    subtitle1 = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        letterSpacing = 0.15.sp
    ),
    subtitle2 = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        letterSpacing = 0.1.sp
    ),
    body1 = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        letterSpacing = 0.5.sp
    ),
    body2 = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        letterSpacing = 0.25.sp
    ),
    button = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        letterSpacing = 1.25.sp
    ),
    caption = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        letterSpacing = 0.4.sp
    ),
    overline = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 12.sp,
        letterSpacing = 1.sp
    )
)

@Stable
class TrendyolColors(
    brand: Color,
    brandSecondary: Color,
    textHelp: Color,
    uiBackground: Color,
    uiBorder: Color,
    rating: Color,
    placeHolder: Color,
    placeHolderHighlight: Color,
    error: Color,
    isDark: Boolean
) {
    var brand by mutableStateOf(brand)
        private set
    var brandSecondary by mutableStateOf(brandSecondary)
        private set
    var textHelp by mutableStateOf(textHelp)
        private set
    var textLink by mutableStateOf(textHelp)
        private set
    var uiBackground by mutableStateOf(uiBackground)
        private set
    var uiBorder by mutableStateOf(uiBorder)
        private set
    var rating by mutableStateOf(rating)
        private set
    var placeHolder by mutableStateOf(placeHolder)
        private set
    var placeHolderHighlight by mutableStateOf(placeHolderHighlight)
        private set
    var error by mutableStateOf(error)
        private set
    var isDark by mutableStateOf(isDark)
        private set

    fun update(other: TrendyolColors) {
        brand = other.brand
        brandSecondary = other.brandSecondary
        uiBackground = other.uiBackground
        uiBorder = other.uiBorder
        placeHolder = other.placeHolder
        rating = other.rating
        error = other.error
        isDark = other.isDark
    }

    fun copy(): TrendyolColors = TrendyolColors(
        brand = brand,
        brandSecondary = brandSecondary,
        textHelp = textHelp,
        uiBackground = uiBackground,
        uiBorder = uiBorder,
        rating = rating,
        placeHolder = placeHolder,
        placeHolderHighlight = placeHolderHighlight,
        error = error,
        isDark = isDark,
    )
}

@Composable
fun TrendyolTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {

    val colors = if (darkTheme) DarkColorPalette else LightColorPalette

    ProvideTrendyolColors(colors) {
        MaterialTheme(
            colors = if (darkTheme) DarkThemeColors else LightThemeColors,
            shapes = AppShapes,
            typography = TrendyolTypography,
            content = content
        )
    }
}

@Composable
fun ProvideTrendyolColors(
    colors: TrendyolColors,
    content: @Composable () -> Unit
) {
    val colorPalette = remember {
        colors.copy()
    }
    colorPalette.update(colors)
    CompositionLocalProvider(LocalTrendyolColors provides colorPalette, content = content)
}

object TrendyolTheme {
    val colors: TrendyolColors
        @Composable
        get() = LocalTrendyolColors.current
}

private val LocalTrendyolColors = staticCompositionLocalOf<TrendyolColors> {
    error("No TrendyolColorsColorPalette provided")
}
