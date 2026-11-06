package ru.practicum.android.diploma.core.designsystem.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import ru.practicum.android.diploma.core.designsystem.theme.Dimens
import ru.practicum.android.diploma.core.designsystem.theme.DiplomaTheme

// Основная кнопка: синий фон, белый текст ("Применить", "Выбрать")
@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AppButton(
        text = text,
        onClick = onClick,
        containerColor = DiplomaTheme.colors.accent,
        contentColor = DiplomaTheme.colors.onAccent,
        modifier = modifier,
    )
}

// Третичная кнопка: без фона, красный текст ("Сбросить")
@Composable
fun TertiaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AppButton(
        text = text,
        onClick = onClick,
        containerColor = Color.Transparent,
        contentColor = DiplomaTheme.colors.attention,
        modifier = modifier,
    )
}

@Composable
private fun AppButton(
    text: String,
    onClick: () -> Unit,
    containerColor: Color,
    contentColor: Color,
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(Dimens.buttonHeight),
        // По умолчанию кнопка M3 полностью скруглена, в макете скругление 12
        shape = RoundedCornerShape(Dimens.cornerRadius),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor,
        ),
        contentPadding = PaddingValues(horizontal = Dimens.spacing8),
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge,
            textAlign = TextAlign.Center,
        )
    }
}

@LightDarkPreviews
@Composable
private fun AppButtonsPreview() {
    ComponentPreview {
        PrimaryButton(text = "Применить", onClick = {}, modifier = Modifier.fillMaxWidth())
        TertiaryButton(text = "Сбросить", onClick = {}, modifier = Modifier.fillMaxWidth())
    }
}
