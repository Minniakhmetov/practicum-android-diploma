package ru.practicum.android.diploma.core.designsystem.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.designsystem.theme.Dimens
import ru.practicum.android.diploma.core.designsystem.theme.DiplomaTheme

// Кнопка-иконка: иконка 24 в тач-зоне 48 (верхняя панель, поле поиска, очистка значения)
@Composable
fun AppIconButton(
    @DrawableRes iconRes: Int,
    contentDescription: String?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    tint: Color = DiplomaTheme.colors.primaryText,
) {
    Box(
        modifier = modifier
            .size(Dimens.iconButtonSize)
            .clip(CircleShape)
            .clickable(role = Role.Button, onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        AppIcon(iconRes = iconRes, tint = tint, contentDescription = contentDescription)
    }
}

// Иконка 24 без нажатия, в рамке 48, чтобы стоять в одну линию с AppIconButton
@Composable
fun FramedIcon(
    @DrawableRes iconRes: Int,
    modifier: Modifier = Modifier,
    tint: Color = DiplomaTheme.colors.primaryText,
) {
    Box(
        modifier = modifier.size(Dimens.iconButtonSize),
        contentAlignment = Alignment.Center,
    ) {
        AppIcon(iconRes = iconRes, tint = tint, contentDescription = null)
    }
}

@Composable
private fun AppIcon(
    @DrawableRes iconRes: Int,
    tint: Color,
    contentDescription: String?,
) {
    Icon(
        painter = painterResource(iconRes),
        contentDescription = contentDescription,
        modifier = Modifier.size(Dimens.iconSize),
        tint = tint,
    )
}

@LightDarkPreviews
@Composable
private fun AppIconButtonPreview() {
    ComponentPreview {
        Row {
            AppIconButton(iconRes = R.drawable.ic_arrow_back_24, contentDescription = null, onClick = {})
            AppIconButton(iconRes = R.drawable.ic_sharing_24, contentDescription = null, onClick = {})
            AppIconButton(
                iconRes = R.drawable.ic_favorites_on_24,
                contentDescription = null,
                onClick = {},
                tint = DiplomaTheme.colors.attention,
            )
            FramedIcon(iconRes = R.drawable.ic_arrow_forward_24)
        }
    }
}
