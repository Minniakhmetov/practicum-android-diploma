package ru.practicum.android.diploma.core.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.designsystem.theme.Dimens
import ru.practicum.android.diploma.core.designsystem.theme.DiplomaTheme

// Верхняя панель: заголовок, необязательная кнопка "Назад" слева и кнопки-иконки справа.
// Отступ под статус-бар добавляет экран.
@Composable
fun AppTopBar(
    title: String,
    modifier: Modifier = Modifier,
    onBackClick: (() -> Unit)?,
    backContentDescription: String?,
    actions: @Composable RowScope.() -> Unit = {},
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(Dimens.topBarHeight)
            .background(DiplomaTheme.colors.background)
            .padding(
                start = if (onBackClick != null) Dimens.topBarStartPaddingWithBack else Dimens.topBarStartPadding,
                end = Dimens.spacing8,
            ),
        horizontalArrangement = Arrangement.spacedBy(Dimens.spacing4),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (onBackClick != null) {
            AppIconButton(
                iconRes = R.drawable.ic_arrow_back_24,
                contentDescription = backContentDescription,
                onClick = onBackClick,
            )
        }
        Text(
            text = title,
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.titleLarge,
            color = DiplomaTheme.colors.primaryText,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        actions()
    }
}

@LightDarkPreviews
@Composable
private fun AppTopBarPreview() {
    ComponentPreview {
        AppTopBar(title = "Поиск вакансий", onBackClick = null, backContentDescription = null) {
            AppIconButton(iconRes = R.drawable.ic_filter_off_24, contentDescription = null, onClick = {})
        }
        AppTopBar(title = "Вакансия", onBackClick = {}, backContentDescription = "Назад") {
            AppIconButton(iconRes = R.drawable.ic_sharing_24, contentDescription = null, onClick = {})
            AppIconButton(
                iconRes = R.drawable.ic_favorites_on_24,
                contentDescription = null,
                onClick = {},
                tint = DiplomaTheme.colors.attention,
            )
        }
        AppTopBar(title = "Избранное", onBackClick = null, backContentDescription = null)
    }
}
