package ru.practicum.android.diploma.core.designsystem.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ru.practicum.android.diploma.core.designsystem.theme.Dimens
import ru.practicum.android.diploma.core.designsystem.theme.DiplomaTheme

// Превью компонента сразу в светлой и тёмной теме
@Preview(name = "Light")
@Preview(name = "Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
annotation class LightDarkPreviews

// Обёртка для превью: тема приложения, фон экрана и отступы между вариантами компонента
@Composable
internal fun ComponentPreview(content: @Composable ColumnScope.() -> Unit) {
    DiplomaTheme {
        Column(
            modifier = Modifier
                .background(DiplomaTheme.colors.background)
                .padding(Dimens.spacing16),
            verticalArrangement = Arrangement.spacedBy(Dimens.spacing8),
            content = content,
        )
    }
}
