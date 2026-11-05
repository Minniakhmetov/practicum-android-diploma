package ru.practicum.android.diploma.core.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.designsystem.theme.Dimens
import ru.practicum.android.diploma.core.designsystem.theme.DiplomaTheme

// Поле ввода с подписью сверху ("Ожидаемая зарплата").
// Подпись: в фокусе синяя, с текстом - чёрная, пустая - серая. Крестик виден в фокусе, когда есть текст.
@Composable
fun AppTextField(
    value: String,
    onValueChange: (String) -> Unit,
    onClearClick: () -> Unit,
    label: String,
    placeholder: String,
    modifier: Modifier = Modifier,
    clearContentDescription: String? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
) {
    val colors = DiplomaTheme.colors
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    val labelColor = when {
        isFocused -> colors.accent
        value.isNotEmpty() -> colors.fieldText
        else -> colors.fieldHint
    }

    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .fillMaxWidth()
            .height(Dimens.textFieldHeight),
        textStyle = MaterialTheme.typography.bodyLarge.copy(color = colors.fieldText),
        keyboardOptions = keyboardOptions,
        singleLine = true,
        interactionSource = interactionSource,
        cursorBrush = SolidColor(colors.accent),
        decorationBox = { innerTextField ->
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colors.fieldBackground, RoundedCornerShape(Dimens.cornerRadius))
                    .padding(start = Dimens.spacing16, end = Dimens.spacing4),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = label,
                        style = MaterialTheme.typography.bodySmall,
                        color = labelColor,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Box {
                        if (value.isEmpty()) {
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
                }
                if (isFocused && value.isNotEmpty()) {
                    AppIconButton(
                        iconRes = R.drawable.ic_close_24,
                        contentDescription = clearContentDescription,
                        onClick = onClearClick,
                        tint = colors.fieldText,
                    )
                }
            }
        },
    )
}

@LightDarkPreviews
@Composable
private fun AppTextFieldPreview() {
    ComponentPreview {
        AppTextField(
            value = "",
            onValueChange = {},
            onClearClick = {},
            label = "Ожидаемая зарплата",
            placeholder = "Введите сумму",
        )
        AppTextField(
            value = "40000",
            onValueChange = {},
            onClearClick = {},
            label = "Ожидаемая зарплата",
            placeholder = "Введите сумму",
        )
    }
}
