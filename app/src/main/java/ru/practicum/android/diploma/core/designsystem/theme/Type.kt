package ru.practicum.android.diploma.core.designsystem.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import ru.practicum.android.diploma.R

val YsDisplay = FontFamily(
    Font(R.font.ys_display_regular, FontWeight.Normal),
    Font(R.font.ys_display_medium, FontWeight.Medium),
    Font(R.font.ys_display_bold, FontWeight.Bold),
)

// Стили дизайн-системы из Figma: начертание, размер / высота строки
private val Bold32 = TextStyle(
    fontFamily = YsDisplay,
    fontWeight = FontWeight.Bold,
    fontSize = Dimens.textSize32,
    lineHeight = Dimens.lineHeight38,
)

private val Medium22 = TextStyle(
    fontFamily = YsDisplay,
    fontWeight = FontWeight.Medium,
    fontSize = Dimens.textSize22,
    lineHeight = Dimens.lineHeight26,
)

private val Medium16 = TextStyle(
    fontFamily = YsDisplay,
    fontWeight = FontWeight.Medium,
    fontSize = Dimens.textSize16,
    lineHeight = Dimens.lineHeight19,
)

private val Regular16 = TextStyle(
    fontFamily = YsDisplay,
    fontWeight = FontWeight.Normal,
    fontSize = Dimens.textSize16,
    lineHeight = Dimens.lineHeight19,
)

private val Regular12 = TextStyle(
    fontFamily = YsDisplay,
    fontWeight = FontWeight.Normal,
    fontSize = Dimens.textSize12,
    lineHeight = Dimens.lineHeight16,
)

// Стили раскладываются по слотам Material 3, чтобы стандартные компоненты
// (Button, TextField, NavigationBar) сразу брали нужный стиль из темы.
val AppTypography = Typography(
    // Bold 32/38: заголовок вакансии, "Над приложением работали"
    headlineLarge = Bold32,
    // Medium 22/26: заголовки TopBar, название вакансии в списке, заголовки секций, текст плейсхолдеров
    titleLarge = Medium22,
    // Medium 16/19: подзаголовки
    titleMedium = Medium16,
    // Medium 16/19: текст кнопок (Button берёт labelLarge)
    labelLarge = Medium16,
    // Regular 16/19: основной текст, поля ввода (TextField берёт bodyLarge)
    bodyLarge = Regular16,
    // Regular 12/16: label поля ввода
    bodySmall = Regular12,
    // Regular 12/16: подписи вкладок (NavigationBar берёт labelMedium)
    labelMedium = Regular12,
)
