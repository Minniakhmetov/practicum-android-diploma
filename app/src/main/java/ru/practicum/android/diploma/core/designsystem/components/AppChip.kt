package ru.practicum.android.diploma.core.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.practicum.android.diploma.core.designsystem.theme.Dimens
import ru.practicum.android.diploma.core.designsystem.theme.DiplomaTheme

// Чип над результатами поиска: "Найдено 286 вакансий", "Таких вакансий нет"
@Composable
fun AppChip(
    text: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = text,
        modifier = modifier
            .background(DiplomaTheme.colors.accent, RoundedCornerShape(Dimens.cornerRadius))
            .padding(horizontal = Dimens.chipHorizontalPadding, vertical = Dimens.chipVerticalPadding),
        style = MaterialTheme.typography.bodyLarge,
        color = DiplomaTheme.colors.onAccent,
    )
}

@LightDarkPreviews
@Composable
private fun AppChipPreview() {
    ComponentPreview {
        AppChip(text = "Найдено 286 вакансий")
        AppChip(text = "Таких вакансий нет")
    }
}
