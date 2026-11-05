package ru.practicum.android.diploma.core.designsystem.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextOverflow
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.designsystem.theme.Dimens
import ru.practicum.android.diploma.core.designsystem.theme.DiplomaTheme

// Пункты списка высотой 60 на экранах фильтра

// Текст и стрелка: страна, регион в списке выбора
@Composable
fun NavigationListItem(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ListItemContainer(modifier = modifier.clickable(onClick = onClick)) {
        ListItemText(text = text, color = DiplomaTheme.colors.primaryText)
        FramedIcon(iconRes = R.drawable.ic_arrow_forward_24)
    }
}

// Текст и чекбокс: "Не показывать без зарплаты"
@Composable
fun CheckboxListItem(
    text: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    ListItemContainer(
        modifier = modifier.toggleable(value = checked, role = Role.Checkbox, onValueChange = onCheckedChange),
    ) {
        ListItemText(text = text, color = DiplomaTheme.colors.primaryText)
        FramedIcon(
            iconRes = if (checked) R.drawable.ic_check_box_on_24 else R.drawable.ic_check_box_off_24,
            tint = DiplomaTheme.colors.accent,
        )
    }
}

// Текст и радиокнопка: отрасль в списке выбора
@Composable
fun RadioListItem(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ListItemContainer(
        modifier = modifier.selectable(selected = selected, role = Role.RadioButton, onClick = onClick),
    ) {
        ListItemText(text = text, color = DiplomaTheme.colors.primaryText)
        FramedIcon(
            iconRes = if (selected) R.drawable.ic_radio_button_on_24 else R.drawable.ic_radio_button_off_24,
            tint = DiplomaTheme.colors.accent,
        )
    }
}

// Параметр фильтра ("Место работы", "Страна", "Регион", "Отрасль").
// Без значения: серая подпись и стрелка. Со значением: подпись сверху, значение и крестик сброса.
@Composable
fun FilterListItem(
    label: String,
    value: String?,
    onClick: () -> Unit,
    onClearClick: () -> Unit,
    modifier: Modifier = Modifier,
    clearContentDescription: String? = null,
) {
    ListItemContainer(modifier = modifier.clickable(onClick = onClick)) {
        if (value == null) {
            ListItemText(text = label, color = DiplomaTheme.colors.inactive)
            FramedIcon(iconRes = R.drawable.ic_arrow_forward_24)
        } else {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = label,
                    style = MaterialTheme.typography.bodySmall,
                    color = DiplomaTheme.colors.primaryText,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = value,
                    style = MaterialTheme.typography.bodyLarge,
                    color = DiplomaTheme.colors.primaryText,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
            AppIconButton(
                iconRes = R.drawable.ic_close_24,
                contentDescription = clearContentDescription,
                onClick = onClearClick,
            )
        }
    }
}

@Composable
private fun ListItemContainer(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(Dimens.listItemHeight)
            .padding(start = Dimens.spacing16, end = Dimens.spacing4),
        horizontalArrangement = Arrangement.spacedBy(Dimens.spacing4),
        verticalAlignment = Alignment.CenterVertically,
        content = content,
    )
}

@Composable
private fun RowScope.ListItemText(
    text: String,
    color: Color,
) {
    Text(
        text = text,
        modifier = Modifier.weight(1f),
        style = MaterialTheme.typography.bodyLarge,
        color = color,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
    )
}

@LightDarkPreviews
@Composable
private fun ListItemsPreview() {
    ComponentPreview {
        NavigationListItem(text = "Москва", onClick = {})
        CheckboxListItem(text = "Не показывать без зарплаты", checked = true, onCheckedChange = {})
        CheckboxListItem(text = "Не показывать без зарплаты", checked = false, onCheckedChange = {})
        RadioListItem(text = "Информационные технологии", selected = true, onClick = {})
        RadioListItem(text = "Образование", selected = false, onClick = {})
        FilterListItem(label = "Место работы", value = null, onClick = {}, onClearClick = {})
        FilterListItem(label = "Место работы", value = "Россия, Москва", onClick = {}, onClearClick = {})
    }
}
