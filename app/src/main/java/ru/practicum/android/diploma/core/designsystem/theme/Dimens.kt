package ru.practicum.android.diploma.core.designsystem.theme

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Общие размеры из дизайн-системы Figma (макет 360x800, значения в dp и sp).
// Размер, нужный только одному экрану, кладём в private val в файле этого экрана.
object Dimens {

    // Шкала отступов
    val spacing4 = 4.dp
    val spacing8 = 8.dp
    val spacing12 = 12.dp
    val spacing16 = 16.dp
    val spacing24 = 24.dp
    val spacing32 = 32.dp

    // Боковой отступ экрана
    val screenHorizontalPadding = spacing16

    // Скругление полей ввода, кнопок, чипов, карточек и логотипа
    val cornerRadius = 12.dp

    // Толщина разделителя нижней панели и рамки логотипа
    val strokeWidth = 1.dp

    // Иконки: сама иконка и тач-зона кнопки с иконкой
    val iconSize = 24.dp
    val iconButtonSize = 48.dp

    // Верхняя панель: высота и отступ слева без кнопки "Назад" и с ней
    val topBarHeight = 64.dp
    val topBarStartPadding = spacing16
    val topBarStartPaddingWithBack = spacing4

    // Нижняя панель навигации (без разделителя)
    val bottomNavigationHeight = 56.dp

    // Поле поиска
    val searchFieldHeight = 56.dp

    // Поле ввода на экране фильтра
    val textFieldHeight = 51.dp

    // Основная кнопка ("Применить", "Сбросить")
    val buttonHeight = 59.dp

    // Чип с количеством найденных вакансий
    val chipHeight = 27.dp
    val chipVerticalPadding = spacing4
    val chipHorizontalPadding = spacing12

    // Пункт списка на экранах фильтра (страна, регион, отрасль)
    val listItemHeight = 60.dp

    // Карточка вакансии в списке: вертикальный отступ и размеры логотипа
    val vacancyItemVerticalPadding = 9.dp
    val logoSize = 48.dp
    val logoPlaceholderIconSize = 32.dp

    // Лоадер
    val loaderSize = 48.dp
    val loaderIndicatorSize = 36.dp

    // Иллюстрация плейсхолдера, отступ до текста под ней и максимальная ширина текста
    val placeholderImageWidth = 328.dp
    val placeholderImageHeight = 223.dp
    val placeholderTextTopPadding = spacing16
    val placeholderTextMaxWidth = 268.dp

    // Размеры текста (YS Display): размер / высота строки
    val textSize32 = 32.sp
    val lineHeight38 = 38.sp
    val textSize22 = 22.sp
    val lineHeight26 = 26.sp
    val textSize16 = 16.sp
    val lineHeight19 = 19.sp
    val textSize12 = 12.sp
    val lineHeight16 = 16.sp
}
