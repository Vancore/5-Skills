package vancore.five_skills.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val DarkColorPalette = darkColors(
    primary = Primary,
    primaryVariant = Primary_Dark,
    secondary = Accent_Primary,
    onPrimary = Grey200,
    onBackground = Grey200,
    background = Primary_Dark,
    error = Rose200,
    onSurface = Grey200
)

private val LightColorPalette = lightColors(
    primary = Grey200,
    primaryVariant = Rose200,
    secondary = Accent_Secondary,
    onBackground = Primary_Dark,
    onPrimary = Primary_Dark,
    error = Rose400,
    onSurface = Grey200

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
fun FiveSkillsTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable() () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    val systemUiController = rememberSystemUiController()
    if (darkTheme) {
        systemUiController.setSystemBarsColor(color = Primary_Dark)
    } else {
        systemUiController.setSystemBarsColor(color = Grey200)
    }


    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}