package ru.practicum.android.diploma.core.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.designsystem.theme.Dimens
import ru.practicum.android.diploma.core.designsystem.theme.DiplomaTheme

// Поле поиска: пустое - иконка лупы, с текстом - крестик очистки.
// По кнопке "Поиск" на клавиатуре и по крестику клавиатура скрывается.
@Composable
fun SearchField(
    query: String,
    onQueryChange: (String) -> Unit,
    onClearClick: () -> Unit,
    placeholder: String,
    clearContentDescription: String,
    modifier: Modifier = Modifier,
    onSearch: () -> Unit = {},
) {
    val colors = DiplomaTheme.colors
    val focusManager = LocalFocusManager.current
    BasicTextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = modifier
            .fillMaxWidth()
            .height(Dimens.searchFieldHeight),
        textStyle = MaterialTheme.typography.bodyLarge.copy(color = colors.fieldText),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(
            onSearch = {
                focusManager.clearFocus()
                onSearch()
            },
        ),
        singleLine = true,
        cursorBrush = SolidColor(colors.accent),
        decorationBox = { innerTextField ->
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colors.fieldBackground, RoundedCornerShape(Dimens.cornerRadius))
                    .padding(start = Dimens.spacing16, end = Dimens.spacing4),
                horizontalArrangement = Arrangement.spacedBy(Dimens.spacing8),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(modifier = Modifier.weight(1f)) {
                    if (query.isEmpty()) {
                        Text(
                            text = placeholder,
                            style = MaterialTheme.typography.bodyLarge,
                            color = colors.fieldHint,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                    innerTextField()
                }
                if (query.isEmpty()) {
                    FramedIcon(iconRes = R.drawable.ic_search_24, tint = colors.fieldText)
                } else {
                    AppIconButton(
                        iconRes = R.drawable.ic_close_24,
                        contentDescription = clearContentDescription,
                        onClick = {
                            focusManager.clearFocus()
                            onClearClick()
                        },
                        tint = colors.fieldText,
                    )
                }
            }
        },
    )
}

@LightDarkPreviews
@Composable
private fun SearchFieldPreview() {
    ComponentPreview {
        SearchField(
            query = "",
            onQueryChange = {},
            onClearClick = {},
            placeholder = "Введите запрос",
            clearContentDescription = "Очистить",
        )
        SearchField(
            query = "Android",
            onQueryChange = {},
            onClearClick = {},
            placeholder = "Введите запрос",
            clearContentDescription = "Очистить",
        )
    }
}
