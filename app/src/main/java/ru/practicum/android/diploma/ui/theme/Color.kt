package ru.practicum.android.diploma.ui.theme

import androidx.compose.ui.graphics.Color

// Палитра из дизайн-системы Figma.
// Светлая и тёмная темы собираются из этих цветов в Theme.kt.

// Black[Day], White[Night], Black Universal: основной текст и иконки в светлой теме, фон в тёмной
val Black = Color(color = 0xFF1A1B22)

// White[Day], Black[Night], White Universal: фон в светлой теме, основной текст и иконки в тёмной
val White = Color(color = 0xFFFDFDFD)

// Поля ввода (светлая тема), карточка работодателя, разделитель нижней панели, рамка логотипа.
// Карточка, разделитель и рамка остаются Light Gray и в тёмной теме
val LightGray = Color(color = 0xFFE6E8EB)

// Hint, неактивные вкладки (alpha 0.75), фон полей ввода в тёмной теме
val Gray = Color(color = 0xFFAEAFB4)

// Акцент: активная вкладка, кнопки, лоадер, чекбокс, ссылки
val Blue = Color(color = 0xFF3772E7)

// Избранное, кнопка "Сбросить"
val Red = Color(color = 0xFFF56B6C)

// Background в Figma: затемнение под bottom sheet
val Overlay = Black.copy(alpha = 0.5f)
