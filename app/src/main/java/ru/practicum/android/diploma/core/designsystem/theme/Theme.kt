package ru.practicum.android.diploma.core.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

// Цвета приложения по смыслу. В экранах берём цвета отсюда: DiplomaTheme.colors.background
@Immutable
data class AppColors(
    // Фон экранов, верхней и нижней панелей, элементов списка
    val background: Color,
    // Основной текст и иконки
    val primaryText: Color,
    // Поле поиска и поля ввода
    val fieldBackground: Color,
    val fieldText: Color,
    val fieldHint: Color,
    // Акцент: активная вкладка, кнопки, чип, лоадер, чекбокс, ссылки
    val accent: Color,
    // Текст и иконки на акцентном фоне
    val onAccent: Color,
    // Неактивные вкладки
    val inactive: Color,
    // Избранное (включено), кнопка "Сбросить"
    val attention: Color,
    // Карточка работодателя на экране вакансии: одинаковая в обеих темах
    val cardBackground: Color,
    val cardText: Color,
    // Разделитель нижней панели, рамка логотипа
    val divider: Color,
    // Затемнение под bottom sheet
    val overlay: Color,
)

private val LightAppColors = AppColors(
    background = White,
    primaryText = Black,
    fieldBackground = LightGray,
    fieldText = Black,
    fieldHint = Gray,
    accent = Blue,
    onAccent = White,
    inactive = Gray,
    attention = Red,
    cardBackground = LightGray,
    cardText = Black,
    divider = LightGray,
    overlay = Overlay,
)

private val DarkAppColors = LightAppColors.copy(
    background = Black,
    primaryText = White,
    fieldBackground = Gray,
    fieldHint = White,
)

// Схемы Material 3 нужны, чтобы стандартные компоненты (Button, TextField, Checkbox)
// по умолчанию были в цветах приложения, а не в фиолетовых цветах Material.
private val LightColorScheme = lightColorScheme(
    primary = Blue,
    onPrimary = White,
    secondary = Blue,
    onSecondary = White,
    background = White,
    onBackground = Black,
    surface = White,
    onSurface = Black,
    surfaceVariant = LightGray,
    onSurfaceVariant = Gray,
    surfaceContainerLowest = White,
    surfaceContainerLow = White,
    surfaceContainer = White,
    surfaceContainerHigh = White,
    surfaceContainerHighest = LightGray,
    outline = LightGray,
    outlineVariant = LightGray,
    error = Red,
    onError = White,
    scrim = Black,
)

private val DarkColorScheme = darkColorScheme(
    primary = Blue,
    onPrimary = White,
    secondary = Blue,
    onSecondary = White,
    background = Black,
    onBackground = White,
    surface = Black,
    onSurface = White,
    surfaceVariant = Gray,
    onSurfaceVariant = White,
    surfaceContainerLowest = Black,
    surfaceContainerLow = Black,
    surfaceContainer = Black,
    surfaceContainerHigh = Black,
    surfaceContainerHighest = Gray,
    outline = LightGray,
    outlineVariant = LightGray,
    error = Red,
    onError = White,
    scrim = Black,
)

private val LocalAppColors = staticCompositionLocalOf { LightAppColors }

@Composable
fun DiplomaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val appColors = if (darkTheme) DarkAppColors else LightAppColors
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    CompositionLocalProvider(LocalAppColors provides appColors) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = AppTypography,
            content = content,
        )
    }
}

object DiplomaTheme {
    val colors: AppColors
        @Composable
        @ReadOnlyComposable
        get() = LocalAppColors.current
}
